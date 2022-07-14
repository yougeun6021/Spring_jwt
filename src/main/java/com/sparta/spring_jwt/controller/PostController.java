package com.sparta.spring_jwt.controller;

import com.sparta.spring_jwt.models.postDto.PostDeleteRequestDto;
import com.sparta.spring_jwt.models.postDto.PostRequestDto;
import com.sparta.spring_jwt.models.postDto.PostUpdateRequestDto;
import com.sparta.spring_jwt.models.post.ListPostInterface;
import com.sparta.spring_jwt.models.post.Post;
import com.sparta.spring_jwt.models.post.PostInterface;
import com.sparta.spring_jwt.models.repository.PostRepository;
import com.sparta.spring_jwt.models.security.UserDetailsImpl;
import com.sparta.spring_jwt.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/api/posts/all")
    public List<Post> getPostsAll(){
        return postRepository.findAll();
    }

    @GetMapping("/api/posts")
    public List<ListPostInterface> getPosts(){
        return postRepository.findAllByOrderByModifiedAtDesc();
    }

    @GetMapping("/api/posts/{id}")
    public PostInterface getPost(@PathVariable Long id){
        return postRepository.findAllById(id);
    }


    @PostMapping("/api/posts")
    public Post getPosts(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal  UserDetailsImpl userDetails){


        String username = userDetails.getUser().getUsername();
        System.out.println(username);
        Post post = new Post(postRequestDto, username);
        postRepository.save(post);
        return post;


    }

    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id, @RequestBody PostDeleteRequestDto postDeleteRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        if(post.getPassword().equals(postDeleteRequestDto.getPassword())){
            postRepository.deleteById(id);
            return id;
        }
        return (long) -1;

    }

    @PutMapping("/api/posts/{id}")
    public Long updatePost(@PathVariable Long id , @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        if (post.getPassword().equals(postUpdateRequestDto.getPassword())) {
            return postService.update(id, postUpdateRequestDto);
        }
        return (long) -1;

    }
}