package com.mohammad.relief.data.entity;

import com.mohammad.relief.data.entity.addiction.AdminAddiction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class GlobalTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long adoptionCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdminAddiction adminAddiction;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin Admin;
}
