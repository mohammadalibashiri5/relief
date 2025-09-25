package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class Addiction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1000)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_type_id")
    private CategoryType categoryType;
    @Enumerated(EnumType.STRING)
    private Severity severityLevel;
    private Integer yearOfAddiction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Visitor user;
    @OneToMany(mappedBy = "addiction" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CheckIn> checkIns = new ArrayList<>();
    private String imageUrl;

}
