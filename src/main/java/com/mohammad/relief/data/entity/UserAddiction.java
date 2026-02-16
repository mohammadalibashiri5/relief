package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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

    LocalDateTime journeyStartDate;

    LocalDateTime lastReplaceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_addiction_id")
    private AdminAddiction adminAddiction;

    private int daysPerWeek;

    private int frequencyPerDay;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Motivation> motivations;

}
