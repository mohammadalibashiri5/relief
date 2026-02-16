package com.mohammad.relief.features.userSide.userAddiction.data;

import com.mohammad.relief.features.userSide.userAddiction.domain.UserAddiction;
import com.mohammad.relief.features.userManagement.domain.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddictionRepository extends JpaRepository<UserAddiction, Long> {
    List<UserAddiction> findByUser(Visitor user);

    Boolean existsByAdminAddiction_NameAndUser(String addictionName, Visitor user);

    Optional<UserAddiction> findByIdAndUser(Long id, Visitor user);
}
