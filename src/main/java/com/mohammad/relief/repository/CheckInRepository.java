package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByUserAndAddiction(User user, Addiction addiction);
}
