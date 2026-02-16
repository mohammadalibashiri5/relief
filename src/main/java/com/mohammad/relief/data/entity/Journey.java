package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.enums.Milestone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  id;
    private boolean isStarted;
    private LocalDate startTime;
    private long daysActive;
    @Enumerated(EnumType.STRING)
    @Column(name = "milestone")
    private Milestone milestone;
    @OneToOne
    private UserAddiction userAddiction;
    private boolean cancelled;
}
