package com.example.social_network.controller;

import com.example.social_network.dto.CreateUserDTO;
import com.example.social_network.dto.GetUserDTO;
import com.example.social_network.dto.LoginUserDTO;
import com.example.social_network.dto.UpdateUserDTO;
import com.example.social_network.model.User;
import com.example.social_network.repository.UserRepository;
import com.example.social_network.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getUsers() {
        List<GetUserDTO> users = userService.getUsers();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable UUID id) {
        GetUserDTO user = userService.getUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(createUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDTO loginUserDTO) {
        String token = userService.loginUser(loginUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
        User user = userService.updateUser(id, updateUserDTO);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}
