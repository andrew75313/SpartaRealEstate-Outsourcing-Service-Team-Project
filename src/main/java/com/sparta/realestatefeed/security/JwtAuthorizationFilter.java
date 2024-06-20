package com.sparta.realestatefeed.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String errorMessage = "토큰이 유효하지 않습니다.";

        try {
            String tokenValue = jwtUtil.getAccessTokenFromHeader(req);

            if (tokenValue == null) {
                tokenValue = jwtUtil.getRefreshTokenFromHeader(req);
                errorMessage = "재로그인을 시도해주세요.";
            }

            if (StringUtils.hasText(tokenValue)) {
                if (!jwtUtil.validateToken(tokenValue)) {
                    throw new UnsupportedJwtException("토큰이 유효하지 않습니다.");
                }

                Claims userInfo = jwtUtil.getUserInfoFromToken(tokenValue);

                try {
                    setAuthentication(userInfo.getSubject());
                } catch (Exception e) {
                    throw new UnsupportedJwtException("토큰이 유효하지 않습니다.");
                }
            }

            filterChain.doFilter(req, res);
        } catch (UnsupportedJwtException e) {
            sendErrorMessage(res, errorMessage);
        }
    }

    public void setAuthentication(String username) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void sendErrorMessage(HttpServletResponse res, String message) throws IOException {

        res.setStatus(HttpStatus.BAD_REQUEST.value());

        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        String jsonResponse = objectMapper.writeValueAsString(Map.of("statusCode", HttpStatus.BAD_REQUEST.value(), "msg", message));

        res.getWriter().write(jsonResponse);
    }
}
