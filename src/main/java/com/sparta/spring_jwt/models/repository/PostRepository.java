package com.sparta.spring_jwt.models.repository;

import com.sparta.spring_jwt.models.post.ListPostInterface;
import com.sparta.spring_jwt.models.post.Post;
import com.sparta.spring_jwt.models.post.PostInterface;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<ListPostInterface> findAllByOrderByModifiedAtDesc();
    PostInterface findAllById(Long id);

}
