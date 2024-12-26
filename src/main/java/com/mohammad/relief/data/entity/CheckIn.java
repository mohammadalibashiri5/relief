package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private LocalDate date;
    private String status;

    public CheckIn(User user, LocalDate date, String status) {
        this.user = user;
        this.date = date;
        this.status = status;
    }
}
