package com.sparta.spring_jwt.service;

import com.sparta.spring_jwt.models.comment.Comment;
import com.sparta.spring_jwt.models.commentDto.CommentRequestDto;
import com.sparta.spring_jwt.models.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findAllByPostIdOrderByModifiedAtDesc(postId);
    }

    public Comment saveComments(CommentRequestDto commentRequestDto, Long postid) {
        Comment comment = new Comment(commentRequestDto,postid);

        commentRepository.save(comment);

        return comment;
    }

    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("아이디가 존재하지 않습니다")
        );
        commentRepository.deleteById(id);
    }

    @Transactional
    public void updateComment(CommentRequestDto commentRequestDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("아이디가 존재하지 않습니다")
        );
        comment.update(commentRequestDto);
    }
}
