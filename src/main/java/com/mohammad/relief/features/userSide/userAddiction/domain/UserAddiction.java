package com.mohammad.relief.features.userSide.userAddiction.domain;

import com.mohammad.relief.features.userSide.motivation.domain.Motivation;
import com.mohammad.relief.features.userManagement.domain.Visitor;
import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserAddiction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Visitor user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime journeyStartDate;

    @LastModifiedDate
    private LocalDateTime lastReplaceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_addiction_id")
    private AdminAddiction adminAddiction;

    private int daysPerWeek;

    private int frequencyPerDay;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Motivation> motivations;


}
