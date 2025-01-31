package com.mohammad.relief.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Break {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")

    private User user;

    @ManyToOne
    @JoinColumn(name = "addiction_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Addiction addiction;

    @ManyToOne
    @JoinColumn(name = "trigger_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Trigger trigger;

    private LocalDateTime breakTime;


}
