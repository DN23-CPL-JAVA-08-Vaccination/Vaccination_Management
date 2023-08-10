package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.VaccinationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVaccinationHistoryService {

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient.
     */
    List<VaccinationHistory> findByVaccinationHistoriesByPatient(Patient patient);

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records to be displayed.
     */
    List<VaccinationHistory> showVaccinationHistory();

    /**
     * LoanHTP
     * Gets the total count of vaccination history records associated with the provided patient.
     */
    long getTotalVaccinationHistoryByPatient(Patient patient);

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient and pagination details.
     */
    List<VaccinationHistory> getVaccinationHistoryByPageAndPatient(int page, int size, Patient patient);

    /**
     * LoanHTP
     * Retrieves a vaccination history record based on the provided ID.
     */
    VaccinationHistory findVaccinationHistoryById(int id);

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided vaccination status, patient, and pagination details.
     */
    List<VaccinationHistory> getVaccinationHistoryByStatusAndPatient(int vaccinationStatusId, Patient patient, int page, int size);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient and optionally a vaccination status.
     */
    Page<VaccinationHistory> listVaccinationHistoryByPatientAndStatusPaged(int patient_id, Integer vaccinationStatusId, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient.
     */
    Page<VaccinationHistory> listVaccinationHistoryByPatientPaged(int patientId, Pageable pageable);
}
