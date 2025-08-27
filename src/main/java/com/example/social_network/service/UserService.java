package com.example.social_network.service;

import com.example.social_network.dto.*;
import com.example.social_network.model.Role;
import com.example.social_network.model.User;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<GetUserDTO> getUsers() {

        return userRepository.findAll().stream().map(user -> new GetUserDTO(user.getUserId(), user.getName(), user.getEmail(),
                user.getRole(), user.getCreatedAt(), user.getPosts().stream().
                map(post -> new GetPostsByUserDTO(post.getPostId(), post.getTitle(), post.getContent(), post.getCreatedAt())).toList())).toList();
    }

    public GetUserDTO getUser(UUID id) {
        Optional<User> getUserById = userRepository.findById(id);

        if (getUserById.isPresent()) {
            User user = getUserById.get();
            return new GetUserDTO(user.getUserId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt(), user.getPosts().stream().
                    map(post -> new GetPostsByUserDTO(post.getPostId(), post.getTitle(), post.getContent(), post.getCreatedAt())).toList());
        } else {
            throw new RuntimeException();
        }
    }

    public User createUser(CreateUserDTO createUserDTO) {
        Optional<User> getUserByEmail = userRepository.findByEmail(createUserDTO.email());

        if (getUserByEmail.isPresent()) {
            throw new RuntimeException();
        }

        var hashPassword = passwordEncoder.encode(createUserDTO.password());

        return userRepository.save(new User(createUserDTO.name(), createUserDTO.email(), hashPassword, new Role(2, "USER")));

    }

    public String loginUser(LoginUserDTO loginUserDTO) {
        var userPassword = new UsernamePasswordAuthenticationToken(loginUserDTO.email(), loginUserDTO.password());
        var auth = this.authenticationManager.authenticate(userPassword);

        return tokenService.generateToken((User) auth.getPrincipal());
    }

    public User updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        Optional<User> getUserById = userRepository.findById(id);

        if (getUserById.isPresent()) {
            Optional<User> getUserByEmail = userRepository.findByEmail(updateUserDTO.email());

            if (getUserByEmail.isPresent() && getUserByEmail.get().getUserId() != id) {
                throw new RuntimeException();
            }

            User updateUser = new User(id, updateUserDTO.name(), updateUserDTO.email(), getUserById.get().getPassword(),
                    getUserById.get().getCreatedAt(), getUserById.get().getRole(), getUserById.get().getPosts());

            return userRepository.save(updateUser);

        } else {
            throw new RuntimeException();
        }
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException();
        }

        userRepository.deleteById(id);
    }
}
