package com.sparta.spring_jwt.models.post;

import com.sparta.spring_jwt.models.TimeStamped;
import com.sparta.spring_jwt.models.postDto.PostRequestDto;
import com.sparta.spring_jwt.models.postDto.PostUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends TimeStamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String password;


    public Post (PostRequestDto postRequestDto, String username){
        this.title = postRequestDto.getTitle();
        this.writer = username;
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();
    }

    public void update (PostUpdateRequestDto postUpdateRequestDto){
        this.content = postUpdateRequestDto.getContent();
        this.title = postUpdateRequestDto.getTitle();
    }


}
