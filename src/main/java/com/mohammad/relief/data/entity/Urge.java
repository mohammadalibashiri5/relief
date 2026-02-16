package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_urge")
@Getter @Setter
public class Urge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Integer intensity;


    private boolean didRelapse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_addiction_id", nullable = false)
    private UserAddiction userAddiction;


    @ManyToMany
    @JoinTable(
            name = "urge_triggers_link",
            joinColumns = @JoinColumn(name = "urge_id"),
            inverseJoinColumns = @JoinColumn(name = "user_trigger_id")
    )
    private List<UserTrigger> userTriggers = new ArrayList<>();

    // 4. (Optional but Recommended) What did you do?
    // Tracks which solutions were used during this specific urge
    @ManyToMany
    @JoinTable(
            name = "urge_solutions_link",
            joinColumns = @JoinColumn(name = "urge_id"),
            inverseJoinColumns = @JoinColumn(name = "tracked_solution_id")
    )
    private List<TrackedSolution> solutionsUsed = new ArrayList<>();


}