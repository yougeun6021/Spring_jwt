package com.sparta.spring_jwt.models.userDto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
