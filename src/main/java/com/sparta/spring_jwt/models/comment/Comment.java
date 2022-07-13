package com.sparta.spring_jwt.models.comment;

import com.sparta.spring_jwt.models.TimeStamped;
import com.sparta.spring_jwt.models.commentDto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String content;


    public Comment(CommentRequestDto commentRequestDto, Long postId){
        this.postId = postId;
        this.content = commentRequestDto.getContent();
    }

    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();
    }
}
