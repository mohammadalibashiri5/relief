package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.CheckIn;
import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
    Optional<CheckIn> findByUserAndAddiction(Visitor user, Addiction addiction);
    Optional<CheckIn> findByUserAndAddictionAndLastCheckinDate(Visitor user, Addiction addiction, LocalDate lastCheckinDate);
}
