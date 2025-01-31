package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Trigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String triggerName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "addiction_id")
    private Addiction addiction;
    private String triggerType;
    private String triggerDescription;
    private String avoidanceStrategy;
    private int repetitionCount = 0;  // Track how often this trigger causes relapse


    public Trigger(String triggerName, User user, Addiction addiction, String triggerType, String triggerDescription, String avoidanceStrategy) {
        this.triggerName = triggerName;
        this.user = user;
        this.addiction = addiction;
        this.triggerType = triggerType;
        this.triggerDescription = triggerDescription;
        this.avoidanceStrategy = avoidanceStrategy;
    }
}
