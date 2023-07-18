package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPositionRepository extends JpaRepository<Position, Integer> {
}
