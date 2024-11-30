package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "\"user\"") // Escapes the table name to avoid conflict with the reserved keyword
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String role;

    private LocalDate birthday;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addiction> addictions = new ArrayList<>();

    public User(String username, String email, String password, LocalDateTime updatedAt, LocalDateTime createdAt, String role, LocalDate birthday) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.role = role;
        this.birthday = birthday;
    }

    public User(String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String role, LocalDate birthday, List<Addiction> addictions) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.birthday = birthday;
        this.addictions = addictions;
    }
    public void addAddiction(Addiction addiction) {
        addiction.setUser(this);
        this.addictions.add(addiction);
    }

    public void removeAddiction(Addiction addiction) {
        addiction.setUser(null);
        this.addictions.remove(addiction);
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
