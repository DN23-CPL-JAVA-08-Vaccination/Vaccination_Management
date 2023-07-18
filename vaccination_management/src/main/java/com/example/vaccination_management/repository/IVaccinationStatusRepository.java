package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccinationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccinationStatusRepository extends JpaRepository<VaccinationStatus, Integer> {
}
