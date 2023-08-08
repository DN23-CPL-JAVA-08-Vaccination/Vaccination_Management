package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {
    Vaccination findById(int id);
}
