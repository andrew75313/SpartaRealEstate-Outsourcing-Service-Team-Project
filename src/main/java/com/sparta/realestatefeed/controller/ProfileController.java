package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.config.JwtConfig;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.ProfileRequestDto;
import com.sparta.realestatefeed.dto.ProfileResponseDto;
import com.sparta.realestatefeed.dto.UserRegisterResponseDto;
import com.sparta.realestatefeed.entity.User;
import com.sparta.realestatefeed.service.ProfileSeervice;
import com.sparta.realestatefeed.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@Validated
public class ProfileController {


    private final ProfileSeervice profileService;



    public ProfileController(ProfileSeervice profileService) {


        this.profileService = profileService;
    }
    @GetMapping("/")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace(JwtConfig.BEARER_PREFIX, "");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        ProfileResponseDto responseDto = profileService.getUserProfile(username);
        CommonDto<ProfileResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "회원 조회", responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateUserProfile(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProfileRequestDto profileRequestDto) {
        String accessToken = authorizationHeader.replace(JwtConfig.BEARER_PREFIX, "");
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        ProfileResponseDto responseDto = profileService.updateUserProfile(userName, profileRequestDto);
        CommonDto<ProfileResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "회원 조회", responseDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    @PutMapping("{userId}/password")
    public ResponseEntity<User> updateUserPassword((@RequestHeader("Authorization") String authorizationHeader, @RequestBody User updatedUser) {
        User user = profileService.updateUserPassword(userId, updatedUser);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
