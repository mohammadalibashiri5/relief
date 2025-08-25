package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import com.mohammad.relief.data.entity.enums.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class UserAddiction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Visitor user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addiction_id")
    private AdminAddiction addiction;
    @Enumerated(EnumType.STRING)
    private Severity severityLevel;
    private Integer yearsOfAddiction;

    @ElementCollection
    private Set<String> triggers = new HashSet<>();

//    @OneToMany(mappedBy = "userAddiction", cascade = CascadeType.ALL)
//    private List<CheckIn> checkIns = new ArrayList<>();
}
