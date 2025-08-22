package com.example.social_network.controller;

import com.example.social_network.dto.CreatePostDTO;
import com.example.social_network.dto.GetPostDTO;
import com.example.social_network.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> createPost(@PathVariable UUID userId, @RequestBody @Valid CreatePostDTO createPostDTO) {
        postService.createPost(createPostDTO, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body("Postagem criada com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<GetPostDTO>> getPosts() {
        List<GetPostDTO> posts = postService.getPosts();

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetPostDTO> getPost(@PathVariable UUID postId) {
        GetPostDTO getPost = postService.getPost(postId);

        return ResponseEntity.status(HttpStatus.OK).body(getPost);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable UUID postId, @RequestBody @Valid CreatePostDTO createPostDTO) {
        postService.updatePost(postId, createPostDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
