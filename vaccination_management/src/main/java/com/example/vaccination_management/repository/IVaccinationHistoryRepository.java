package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccinationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IVaccinationHistoryRepository extends JpaRepository<VaccinationHistory, Integer> {
}
