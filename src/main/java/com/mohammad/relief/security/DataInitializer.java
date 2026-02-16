package com.mohammad.relief.security;

import com.mohammad.relief.data.entity.Admin;
import com.mohammad.relief.data.entity.enums.RoleType;
import com.mohammad.relief.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Admin admin = new Admin();
            String hashedPassed = passwordEncoder.encode(adminPassword);
            admin.setEmail(adminEmail);
            admin.setPassword(hashedPassed);
            admin.setRole(RoleType.ROLE_SUPER_ADMIN);
            admin.setName("Mohammad Ali");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setId(UUID.randomUUID());
            admin.setFamilyName("Bashiri");
            admin.setDateOfBirth(LocalDate.of(1996,8,3));
            admin.setUsername("mohammadalibashiri5");

            userRepository.save(admin);
        }

    }
}
