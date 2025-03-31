package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@RequiredArgsConstructor
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
    private Integer yearOfAddiction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Visitor user;
    private String imageUrl;

}
