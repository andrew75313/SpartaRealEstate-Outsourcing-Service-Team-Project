package com.sparta.realestatefeed.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordRequestDto {
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_])[a-zA-Z0-9!@#$%^&*?_]+$",message = "비밀번호에 소문자, 대문자 특수만자 하나씩 포함해서 써주세요")
    private String currentPassword;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_])[a-zA-Z0-9!@#$%^&*?_]+$",message = "비밀번호에 소문자, 대문자 특수만자 하나씩 포함해서 써주세요")
    private String newPassword;

    @Size(min = 8, max = 15)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_])[a-zA-Z0-9!@#$%^&*?_]+$",message = "비밀번호에 소문자, 대문자 특수만자 하나씩 포함해서 써주세요")
    private String checkNewPassword;

    public boolean isNewPasswordMatch() {

        return this.checkNewPassword.equals(this.newPassword);
    }

    public boolean isPasswordMatching() {

        return this.newPassword.equals(this.currentPassword);
    }
}
