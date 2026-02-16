package com.mohammad.relief.features.adminSide.milestoneTemplate.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MilestoneTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer daysRequired;

    @Column(nullable = false)
    private String title;

    private String description;

    private String iconUrl;

}
