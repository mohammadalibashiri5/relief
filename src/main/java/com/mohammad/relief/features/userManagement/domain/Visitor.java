package com.mohammad.relief.features.userManagement.domain;

import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddiction;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.List;


@RequiredArgsConstructor
@Entity
@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class Visitor extends User {
    @OneToMany(mappedBy = "user")
    private List<UserAddiction> addictions;

}
