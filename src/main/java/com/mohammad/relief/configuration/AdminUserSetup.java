package com.mohammad.relief.configuration;

import com.mohammad.relief.data.entity.User;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;

@Configuration
public class AdminUserSetup {

    @Bean
    public CommandLineRunner setupDefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.findByUsername("admin").isPresent()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setEmail("admin@example.com");
                adminUser.setPassword(passwordEncoder.encode("admin123"));
                adminUser.setCreatedAt(LocalDateTime.now());
                adminUser.setUpdatedAt(LocalDateTime.now());
                userRepository.save(adminUser);
                System.out.println("Admin user created: admin / admin123");
            }
        };
    }
}

