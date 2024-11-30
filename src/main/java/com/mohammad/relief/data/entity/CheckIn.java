package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.time.LocalDateTime;
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private User user;
    private LocalDateTime date;
    private String status;

    public CheckIn(User user, LocalDateTime date, String status) {
        this.user = user;
        this.date = date;
        this.status = status;
    }
}
