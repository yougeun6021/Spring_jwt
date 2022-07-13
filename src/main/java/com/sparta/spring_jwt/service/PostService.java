package com.sparta.spring_jwt.service;

import com.sparta.spring_jwt.models.postDto.PostUpdateRequestDto;
import com.sparta.spring_jwt.models.post.Post;
import com.sparta.spring_jwt.models.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;


    @Transactional
    public Long update(Long id , PostUpdateRequestDto postUpdateRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        post.update(postUpdateRequestDto);
        return id;
    }


}
