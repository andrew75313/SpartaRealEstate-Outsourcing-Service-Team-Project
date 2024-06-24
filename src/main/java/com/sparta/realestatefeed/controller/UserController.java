package com.sparta.realestatefeed.controller;

import com.sparta.realestatefeed.config.JwtConfig;
import com.sparta.realestatefeed.dto.CommonDto;
import com.sparta.realestatefeed.dto.QnAResponseDto;
import com.sparta.realestatefeed.dto.UserRegisterRequestDto;
import com.sparta.realestatefeed.dto.UserRegisterResponseDto;
import com.sparta.realestatefeed.service.AuthenticationService;
import com.sparta.realestatefeed.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;


    public UserController(UserService userService, AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto, BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> responseList = new ArrayList<>();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                responseList.add( fieldError.getDefaultMessage()+"\n");
            }
            return ResponseEntity.badRequest().body(responseList);
        }

        try{
            UserRegisterResponseDto responseDto = userService.registerUser(userRegisterRequestDto);
            CommonDto<UserRegisterResponseDto> response = new CommonDto<>(HttpStatus.OK.value(), "회원가입", responseDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("이미 존재하는 User ID 입니다. 회원가입에 실패하셨습니다.");
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().body("잘못된 비밀번호 형식입니다. 회원가입에 실패하셨습니다.");
        }
    }

    @PostMapping("/logout")
    private ResponseEntity<?> userLogout(@RequestHeader("Authorization") String authorizationHeader) {

        try {
            String accessToken = authorizationHeader.replace(JwtConfig.BEARER_PREFIX, "");

            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            authenticationService.logoutUser(accessToken, username);

            SecurityContextHolder.clearContext();

            return ResponseEntity.ok().body("로그아웃되었습니다.");
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().body("아이디 또는 비밀번호를 확인해주세요. 로그인에 실패하셨습니다.");
        }
    }


}
