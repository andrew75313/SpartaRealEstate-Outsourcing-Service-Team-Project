package com.sparta.realestatefeed.repository;

import com.sparta.realestatefeed.entity.RefreshToken;
import com.sparta.realestatefeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(Long id);

    void deleteByUser(User user);
}
