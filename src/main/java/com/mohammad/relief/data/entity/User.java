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

    private String name;

    private String familyName;

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

    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addiction> addictions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckIn> checkIns = new ArrayList<>();

    private Integer globalStreak;
    private Integer totalCheckIns;  // Optional for milestones
    private Integer missedCheckIns;  // Optional to track misses

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Trigger> triggers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_achievements",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements = new ArrayList<>();

    public User(String username, String email, String password, LocalDateTime updatedAt, LocalDateTime createdAt, String role, LocalDate dateOfBirth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
    }

    public User(String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String role, LocalDate dateOfBirth, List<Addiction> addictions) {
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.addictions = addictions;
    }

    public User(String name, String familyName, String username, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String role, LocalDate dateOfBirth, List<Addiction> addictions, List<CheckIn> checkIns, Integer globalStreak, Integer totalCheckIns, Integer missedCheckIns, List<Trigger> triggers, List<Achievement> achievements) {
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.addictions = addictions;
        this.checkIns = checkIns;
        this.globalStreak= globalStreak;
        this.totalCheckIns = totalCheckIns;
        this.missedCheckIns = missedCheckIns;
        this.triggers = triggers;
        this.achievements = achievements;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
