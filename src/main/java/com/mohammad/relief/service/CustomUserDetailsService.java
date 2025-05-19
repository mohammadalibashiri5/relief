package com.mohammad.relief.service;


import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Load user from the database
        Optional<Visitor> userOptional = userRepository.findByEmail(email);

        Visitor user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("Visitor not found with email: " + email));

        // Return a Spring Security Visitor object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Hashed password
                .roles("USER") // You can add roles if applicable
                .build();
    }
}