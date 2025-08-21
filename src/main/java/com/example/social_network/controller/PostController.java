package com.example.social_network.controller;

import com.example.social_network.dto.CreatePostDTO;
import com.example.social_network.dto.GetPostsDTO;
import com.example.social_network.model.Post;
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
    public ResponseEntity<List<GetPostsDTO>> getPosts() {
        List<GetPostsDTO> posts = postService.getPosts();

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
