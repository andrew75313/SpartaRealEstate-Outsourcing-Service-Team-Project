package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
public class UserRegisterRequestDto {

    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]*$",message = "영어 소문자와 숫자만 입력해주세요.")
    private String userName;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_])[a-zA-Z0-9!@#$%^&*?_]+$",message = "비밀번호에 소문자, 대문자 특수만자 하나씩 포함해서 써주세요")
    private String password;
    private String nickName;

    @Email
    private String email;
    private String info;
    private UserRoleEnum role;

}
