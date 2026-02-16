package com.mohammad.relief.features.adminSide.globalTrigger.domain;

import com.mohammad.relief.features.adminSide.aminAddiction.domain.AdminAddiction;
import com.mohammad.relief.features.userManagement.domain.Admin;
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
