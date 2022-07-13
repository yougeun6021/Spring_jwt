package com.sparta.spring_jwt.models.postDto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private String title;

    private String content;

    private String password;

}
