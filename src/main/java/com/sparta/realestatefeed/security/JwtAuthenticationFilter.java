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
import lombok.extern.slf4j.Slf4j;
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
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(user.getUserName(), user.getRole());
        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);
        String refreshToken = jwtUtil.createRefreshToken(user.getUserName());
        response.addHeader(JwtConfig.REFRESH_TOKEN_HEADER, refreshToken);

        RefreshToken existingToken = refreshTokenRepository.findByUserId(user.getId()).orElse(null);

        if (existingToken != null) {
            refreshTokenRepository.delete(existingToken);
        }

        refreshTokenRepository.save(new RefreshToken(refreshToken, user));

        sendMessage(response, "로그인에 성공하였습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        sendMessage(response, "로그인에 실패하였습니다.");
    }

    private void sendMessage(HttpServletResponse res, String message) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        String jsonResponse = objectMapper.writeValueAsString(Map.of("statusCode", res.getStatus(), "msg", message));

        res.getWriter().write(jsonResponse);
    }
}
