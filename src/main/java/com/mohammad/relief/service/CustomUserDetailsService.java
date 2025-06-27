package com.mohammad.relief.service;


import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.Visitor;
import com.mohammad.relief.repository.AdminRepository;
import com.mohammad.relief.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        Optional<Visitor> visitorOptional = userRepository.findByEmail(email);
        if (visitorOptional.isPresent()) {
            Visitor visitor = visitorOptional.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(visitor.getEmail())
                    .password(visitor.getPassword())
                    .roles("VISITOR")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

}