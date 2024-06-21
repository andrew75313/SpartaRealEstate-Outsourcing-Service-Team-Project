package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {

    @Size(min = 10, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    private String userName;

    @Size(min = 10)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_])*$")
    private String password; // 로그인 Password

    private String nickName; // 이름

    @Email
    private String email;
    private String info;



    private UserRoleEnum roel;


}
