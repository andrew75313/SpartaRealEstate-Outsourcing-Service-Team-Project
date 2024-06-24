package com.sparta.realestatefeed.dto;

import com.sparta.realestatefeed.entity.User;
import lombok.Getter;

@Getter
public class UserRegisterResponseDto {
    String userName;
    String nickName;
    String email;
    String info;

    public UserRegisterResponseDto(User user) {
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        this.info = user.getInfo();
    }
}

