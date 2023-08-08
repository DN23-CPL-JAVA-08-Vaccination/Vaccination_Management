package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccinationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface IVaccinationHistoryRepository extends JpaRepository<VaccinationHistory, Integer> {
    VaccinationHistory findById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE vaccination_history SET vaccination_status_id = ?1 WHERE vaccination_history.id = ?2",nativeQuery = true)
    void updateStatusVaccinationHistory(Integer statusId, Integer vaccinationHR);
}
