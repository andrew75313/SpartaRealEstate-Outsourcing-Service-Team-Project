package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.dto.*;
import com.sparta.realestatefeed.exception.PasswordMismatchException;
import com.sparta.realestatefeed.exception.UserNotFoundException;
import com.sparta.realestatefeed.security.UserDetailsImpl;
import com.sparta.realestatefeed.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ProfileController {


    private final ProfileService profileService;

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);



    public ProfileController(ProfileService profileService) {


        this.profileService = profileService;
    }
    @GetMapping("/profiles")
    public ResponseEntity<?> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        HttpHeaders headers = new HttpHeaders();

        try{
            String userName = userDetails.getUser().getUserName();

            ProfileResponseDto responseDto = profileService.getUserProfile(userName);
            CommonDto<ProfileResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "회원 조회", responseDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (UserNotFoundException e) {
            headers.add("Message", "해당 사용자를 찾을 수 없습니다.");
            return ResponseEntity.notFound().headers(headers).build();
        } catch (AccessDeniedException e) {
            headers.add("Message", "인증되지 않은 사용자입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
        } catch (RuntimeException e) {
            String errorMessage = "Profile Error: " + e.getMessage();
            headers.add("Message", errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
        }

    }

    @PutMapping("/profiles")
    public ResponseEntity<?> updateUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        try{
            String userName = userDetails.getUser().getUserName();

            ProfileResponseDto responseDto = profileService.updateUserProfile(userName, profileRequestDto);
            CommonDto<ProfileResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "회원 조회", responseDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (UserNotFoundException e) {
            headers.add("Message", "해당 사용자를 찾을 수 없습니다.");
            return ResponseEntity.notFound().headers(headers).build();
        } catch (AccessDeniedException e) {
            headers.add("Message", "인증되지 않은 사용자입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
        } catch (RuntimeException e) {
            String errorMessage = "Profile Error: " + e.getMessage();
            headers.add("Message", errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
        }

    }

    @PutMapping("/profiles/password")
    public ResponseEntity<?> updateUserPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody PasswordRequestDto passwordRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        try {
            String userName = userDetails.getUser().getUserName();
            profileService.updateUserPassword(userName, passwordRequestDto);
            return ResponseEntity.ok().body("비밀번호가 변경되었습니다.");
        } catch (UserNotFoundException e) {
            headers.add("Message", "해당 사용자를 찾을 수 없습니다.");
            logger.error("UserNotFoundException: {}", e.getMessage());
            return ResponseEntity.notFound().headers(headers).build();
        } catch (AccessDeniedException e) {
            headers.add("Message", "인증되지 않은 사용자입니다.");
            logger.error("AccessDeniedException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).build();
        } catch (PasswordMismatchException e) {
            headers.add("Message", e.getMessage());
            logger.error("PasswordMismatchException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        } catch (IllegalArgumentException e) {
            headers.add("Message", e.getMessage());
            logger.error("IllegalArgumentException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).build();
        } catch (RuntimeException e) {
            String errorMessage = "Profile Password Change Error: " + e.getMessage();
            headers.add("Message", errorMessage);
            logger.error("RuntimeException: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).build();
        }
    }
}
