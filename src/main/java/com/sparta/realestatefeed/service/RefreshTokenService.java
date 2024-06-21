package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
