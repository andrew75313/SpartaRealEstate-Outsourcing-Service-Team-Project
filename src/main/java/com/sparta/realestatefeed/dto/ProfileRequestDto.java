package com.sparta.realestatefeed.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class ProfileRequestDto {
    String nickName;
    @Email
    String email;
    String info;
}
