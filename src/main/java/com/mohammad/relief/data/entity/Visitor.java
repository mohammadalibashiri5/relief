package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Entity
@Setter
@Getter
public class Visitor extends User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addiction> addictions = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Trigger> triggers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_achievements",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements = new ArrayList<>();

}
