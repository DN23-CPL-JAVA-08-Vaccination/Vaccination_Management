package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {
    Long countById(int vaccineTypeID);
}
