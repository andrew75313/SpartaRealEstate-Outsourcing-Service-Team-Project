package com.sparta.realestatefeed.entity;


import com.sparta.realestatefeed.dto.ProfileRequestDto;
import com.sparta.realestatefeed.dto.UserRegisterRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @Column(nullable = false, unique = true)
    private String userName;

    @NotNull
    @Column(nullable = false, length = 100)
    private String password;

    private String nickName;
    private String info;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    public User(UserRegisterRequestDto requestDto) {
        this.userName = requestDto.getUserName();
        this.password = requestDto.getPassword();
        this.nickName = requestDto.getNickName();
        this.info = requestDto.getInfo();
        this.email = requestDto.getEmail();
        this.role = requestDto.getRole();
    }


    public void updateProfile(ProfileRequestDto profileRequestDto) {
        this.nickName = profileRequestDto.getNickName();
        this.info = profileRequestDto.getInfo();
    }

    @ElementCollection
    @CollectionTable(name = "user_previous_passwords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "previous_password")
    private List<String> previousPasswords = new ArrayList<>();
}
