package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.enums.StreakLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "addiction_id")
    private Addiction addiction;
    private LocalDate startDate;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDate lastCheckinDate;
    private Enum<StreakLevel> level;

}
