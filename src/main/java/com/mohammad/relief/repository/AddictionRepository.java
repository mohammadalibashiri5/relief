package com.mohammad.relief.repository;

import com.mohammad.relief.data.entity.Addiction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddictionRepository extends JpaRepository<Addiction,Integer> {
}
