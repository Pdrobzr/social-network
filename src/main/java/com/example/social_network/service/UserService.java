package com.example.social_network.service;

import com.example.social_network.dto.CreateUserDTO;
import com.example.social_network.dto.UpdateUserDTO;
import com.example.social_network.model.User;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(UUID id) {
        Optional<User> getUserById = userRepository.findById(id);

        if(getUserById.isPresent()) {
            return getUserById.get();
        } else {
            throw new RuntimeException();
        }
    }

    public User createUser(CreateUserDTO createUserDTO) {
        Optional<User> getUserByEmail = userRepository.findByEmail(createUserDTO.email());

        if(getUserByEmail.isPresent()) {
            throw new RuntimeException();
        }

        var hashPassword = passwordEncoder.encode(createUserDTO.password());
        User user = userRepository.save(new User(createUserDTO.name(), createUserDTO.email(), hashPassword));

        return user;

    }

    public User updateUser(UUID id, UpdateUserDTO updateUserDTO) {
        Optional<User> getUserById = userRepository.findById(id);

        if(getUserById.isPresent()) {
            Optional<User> getUserByEmail = userRepository.findByEmail(updateUserDTO.email());

            if(getUserByEmail.isPresent() && getUserByEmail.get().getUserId() != id) {
                throw new RuntimeException();
            }

            User updateUser = new User(id, updateUserDTO.name(), updateUserDTO.email(), getUserById.get().getPassword(), getUserById.get().getCreatedAt());

            return userRepository.save(updateUser);

        } else {
            throw new RuntimeException();
        }
    }

    public void deleteUser(UUID id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException();
        }

        userRepository.deleteById(id);
    }
}
