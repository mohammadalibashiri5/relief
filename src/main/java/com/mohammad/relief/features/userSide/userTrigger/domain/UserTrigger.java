package com.mohammad.relief.features.userSide.userTrigger.domain;

import com.mohammad.relief.features.userManagement.domain.Visitor;
import com.mohammad.relief.features.userSide.urge.domain.Urge;
import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddiction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class UserTrigger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Visitor user;
    @ManyToOne
    @JoinColumn(name = "user_addiction_id")
    private UserAddiction userAddiction;
    @ManyToMany(mappedBy = "userTriggers")
    private List<Urge> associatedUrges;

}
