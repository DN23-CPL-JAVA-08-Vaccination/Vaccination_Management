package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ILocationRepository extends JpaRepository<Location, Integer> {
}
