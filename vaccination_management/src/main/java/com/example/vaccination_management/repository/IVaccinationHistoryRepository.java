package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.VaccinationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

public interface IVaccinationHistoryRepository extends JpaRepository<VaccinationHistory, Integer> {

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient.
     */
    List<VaccinationHistory> findVaccinationHistoriesByPatient(Patient patient);

    /**
     * LoanHTP
     * Counts the number of vaccination history records associated with the provided patient.
     */
    long countVaccinationHistoriesByPatient(Patient patient);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient.
     */
    Page<VaccinationHistory> findByPatient(Patient patient, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided vaccination status ID and patient.
     */
    Page<VaccinationHistory> findByVaccinationStatusIdAndPatient(int vaccinationStatusId, Patient patient, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient ID and optionally a vaccination status ID.
     */
    @Query("SELECT vh FROM VaccinationHistory vh WHERE vh.patient.id = :patientId AND (:vaccinationStatusId IS NULL OR vh.vaccinationStatus.id = :vaccinationStatusId)")
    public Page<VaccinationHistory> listVaccinationHistoryByPatientAndStatusPaged(@Param("patientId") int patientId, @Param("vaccinationStatusId") Integer vaccinationStatusId, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient ID.
     */
    public Page<VaccinationHistory> findByPatientId(int patientId, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO vaccination_history(delete_flag, dosage, end_time, guardian_name, guardian_phone, start_time, vaccination_times, patient_id, vaccination_id, vaccination_status_id) \n" +
            "VALUES(?1,?2,?3,?4, ?5, ?6, ?7, ?8, ?9, ?10)",nativeQuery = true)
    void insertVaccinationHTR(Boolean deleteFlag, Double dosage, LocalDateTime endTime, String guardianName, String guardianPhone, LocalDateTime startTime, int vaccinationTimes, int patientId, int vaccinationId, int vaccinationStatusId);
}
