package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String triggerName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Visitor user;
    @ManyToOne
    @JoinColumn(name = "addiction_id")
    private Addiction addiction;
    private String triggerType;
    private String triggerDescription;
    private String avoidanceStrategy;
    private int repetitionCount = 0;  // Track how often this trigger causes relapse
}
