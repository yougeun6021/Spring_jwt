package com.sparta.spring_jwt.models.repository;

import com.sparta.spring_jwt.models.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostIdOrderByModifiedAtDesc(Long postId);

}
