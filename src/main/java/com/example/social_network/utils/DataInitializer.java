package com.example.social_network.utils;

import com.example.social_network.model.Role;
import com.example.social_network.model.User;
import com.example.social_network.repository.RoleRepository;
import com.example.social_network.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            if (roleRepository.findByRoleName("ADMIN") == null) {
                Role adminRole = roleRepository.save(new Role(1,"ADMIN"));

                User adminUser = userRepository.save(new User("ADMINISTRADOR", "admin@admin", passwordEncoder.encode("123"), adminRole));

                System.out.println(">>> ADMIN criado: " + adminUser);
            }
            if (roleRepository.findByRoleName("USER") == null) {
                roleRepository.save(new Role(2, "USER"));
            }
        };
    }
}
