package com.sparta.realestatefeed.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.realestatefeed.config.JwtConfig;
import com.sparta.realestatefeed.dto.LoginRequestDto;
import com.sparta.realestatefeed.entity.RefreshToken;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.RefreshTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("아이디/비밀번호를 반드시 입력해주세요.");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(user.getUserName(), user.getUserRoleEnum());
        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);
        String refreshToken = jwtUtil.createRefreshToken(user.getUserName());
        response.addHeader(JwtConfig.REFRESH_TOKEN_HEADER, refreshToken);

        //DB 리프레시 토큰 확인
        RefreshToken checkToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseGet(() -> {
                    return refreshTokenRepository.save(new RefreshToken(refreshToken, user));
                });

        // 있던 토큰 삭제하고 새로 위에서 발급한 토큰 넣기
        if (checkToken != null) {
            refreshTokenRepository.delete(checkToken);
            refreshTokenRepository.save(new RefreshToken(refreshToken, user));
        }

        sendMessage(response, "로그인에 성공하였습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        response.setStatus(401);

        sendMessage(response, "로그인에 실패하였습니다");
    }

    private void sendMessage(HttpServletResponse res, String message) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        String jsonResponse = objectMapper.writeValueAsString(Map.of("statusCode", HttpStatus.BAD_REQUEST.value(), "msg", message));

        res.getWriter().write(jsonResponse);
    }
}
