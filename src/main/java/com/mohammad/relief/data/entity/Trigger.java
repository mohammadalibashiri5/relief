package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_addiction_id")
    private UserAddiction userAddiction;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trigger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Solution> solutions = new ArrayList<>();
    private String description;
    private int repetitionCount = 0;
    private LocalDateTime createdAt;
}
