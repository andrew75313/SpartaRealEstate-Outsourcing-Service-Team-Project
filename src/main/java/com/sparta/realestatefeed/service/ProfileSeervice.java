package com.sparta.realestatefeed.service;

import com.sparta.realestatefeed.dto.ProfileRequestDto;
import com.sparta.realestatefeed.dto.ProfileResponseDto;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileSeervice {

    private UserRepository userRepository;

    public ProfileSeervice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public ProfileResponseDto getUserProfile(String userName) {

        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );
        return new ProfileResponseDto(user);

    }

    public ProfileResponseDto updateUserProfile(String userName, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 유저는 존재하지 않습니다.")
        );

        user.updateProfile(profileRequestDto);

        userRepository.save(user);

        return new ProfileResponseDto(user);


    }
}
