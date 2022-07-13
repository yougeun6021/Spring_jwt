package com.sparta.spring_jwt.models.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignupRequestDto {
    private String username;
    private String password;
    private String confirm_password;
}