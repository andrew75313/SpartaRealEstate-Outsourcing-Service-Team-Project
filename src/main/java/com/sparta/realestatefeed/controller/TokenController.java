package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    public TokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<String> createToken(HttpServletRequest request, HttpServletResponse response) {

        return refreshTokenService.refreshToken(request, response);
    }
}
