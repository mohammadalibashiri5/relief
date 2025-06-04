package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Addiction;
import com.mohammad.relief.data.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddictionRepository extends JpaRepository<Addiction,Integer> {
    Optional<Addiction> findByName(String name);
    List<Addiction> findByUser(Visitor user);
    Optional<Addiction> findByUserAndId(Visitor user, Long id);
    Optional<Addiction> findByUserAndName(Visitor user, String name);
}
