package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Addiction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(length = 1000)
    private String description;
    @Enumerated(EnumType.STRING)
    private Severity severityLevel;
    private Integer yearsOfAddiction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Addiction(String name, String description, Severity severityLevel, Integer yearsOfAddiction, User user) {
        this.name = name;
        this.description = description;
        this.severityLevel = severityLevel;
        this.yearsOfAddiction = yearsOfAddiction;
        this.user = user;
    }

    public Addiction(String name, String description, Severity severityLevel, Integer yearsOfAddiction) {
        this.name = name;
        this.description = description;
        this.severityLevel = severityLevel;
        this.yearsOfAddiction = yearsOfAddiction;
    }
}
