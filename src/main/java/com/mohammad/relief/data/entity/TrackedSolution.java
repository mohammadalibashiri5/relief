package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class TrackedSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor user;
    @ManyToOne
    @JoinColumn(name = "global_solution_id")
    private GlobalSolution globalSolution;
    private Integer personalRating;
    private String title;
    private String personalNote;
}
