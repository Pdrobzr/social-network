package com.example.social_network.service;

import com.example.social_network.dto.CreatePostDTO;
import com.example.social_network.dto.GetPostsDTO;
import com.example.social_network.model.Post;
import com.example.social_network.model.User;
import com.example.social_network.repository.PostRepository;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public void createPost(CreatePostDTO createPostDTO, UUID userId) {
        Optional<User> getUser = userRepository.findById(userId);

        if(getUser.isPresent()) {
            Post createPost = new Post(createPostDTO.content(), getUser.get());
            postRepository.save(createPost);
        } else {
            throw new RuntimeException();
        }
    }

    public List<GetPostsDTO> getPosts() {
        return postRepository.findAll().stream().map(post -> new GetPostsDTO(post.getPostId(), post.getContent(), post.getCreatedAt(),
                post.getUser().getUserId())).toList();
    }
}
