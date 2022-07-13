package com.sparta.spring_jwt.controller;

import com.sparta.spring_jwt.models.comment.Comment;
import com.sparta.spring_jwt.models.commentDto.CommentRequestDto;
import com.sparta.spring_jwt.models.security.UserDetailsImpl;
import com.sparta.spring_jwt.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/api/comments/{postid}")
    public List<Comment> getComments(@PathVariable Long postid){
        return commentService.getComments(postid);
    }

    @PostMapping("/api/comments/{postid}")
    public Comment saveComment(@RequestBody CommentRequestDto commentRequestDto , @PathVariable Long postid){
        Comment comment = commentService.saveComments(commentRequestDto,postid);

        return comment;
    }

    @DeleteMapping("/api/comments/{id}")
    public Long deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
        return id;
    }

    @PutMapping("/api/comments/{id}")
    public Long updateComment(@RequestBody CommentRequestDto commentRequestDto , @PathVariable Long id){
        commentService.updateComment(commentRequestDto,id);

        return id;
    }





}
