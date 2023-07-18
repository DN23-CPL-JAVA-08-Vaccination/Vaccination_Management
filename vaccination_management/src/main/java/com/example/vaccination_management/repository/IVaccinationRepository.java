package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {
}
