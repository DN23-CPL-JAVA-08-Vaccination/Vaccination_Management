package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.service.IVaccinationHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VaccinationHistoryService implements IVaccinationHistoryService {

    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient.
     */
    @Override
    public List<VaccinationHistory> findByVaccinationHistoriesByPatient(Patient patient) {
        return iVaccinationHistoryRepository.findVaccinationHistoriesByPatient(patient);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records to be displayed.
     */
    @Override
    public List<VaccinationHistory> showVaccinationHistory() {
        return iVaccinationHistoryRepository.findAll();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccination history records associated with the provided patient.
     */
    @Override
    public long getTotalVaccinationHistoryByPatient(Patient patient) {
        return iVaccinationHistoryRepository.countVaccinationHistoriesByPatient(patient);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient and pagination details.
     */
    @Override
    public List<VaccinationHistory> getVaccinationHistoryByPageAndPatient(int page, int size, Patient patient) {
        Pageable pageable = PageRequest.of(page,size);
        Page<VaccinationHistory> vaccinationHistoryPage = iVaccinationHistoryRepository.findByPatient(patient, pageable);
        return vaccinationHistoryPage.getContent();
    }

    /**
     * LoanHTP
     * Retrieves a vaccination history record based on the provided ID.
     */
    @Override
    public VaccinationHistory findVaccinationHistoryById(int id) {
        return iVaccinationHistoryRepository.findById(id).orElse(null);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided vaccination status, patient, and pagination details.
     */
    @Override
    public List<VaccinationHistory> getVaccinationHistoryByStatusAndPatient(int vaccinationStatusId, Patient patient, int page, int size) {
        Page<VaccinationHistory> pageResult = iVaccinationHistoryRepository.findByVaccinationStatusIdAndPatient(vaccinationStatusId, patient, PageRequest.of(page, size));
        return pageResult.getContent();
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient and optionally a vaccination status.
     */
    @Override
    public Page<VaccinationHistory> listVaccinationHistoryByPatientAndStatusPaged(int patient_id, Integer vaccinationStatusId, Pageable pageable) {
        return iVaccinationHistoryRepository.listVaccinationHistoryByPatientAndStatusPaged(patient_id, vaccinationStatusId, pageable);    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient.
     */
    @Override
    public Page<VaccinationHistory> listVaccinationHistoryByPatientPaged(int patientId, Pageable pageable) {
        return iVaccinationHistoryRepository.findByPatientId(patientId, pageable);
    }


    @Override
    public void insertVaccinationHTR(Boolean deleteFlag, Double dosage, LocalDateTime endTime, String guardianName, String guardianPhone, LocalDateTime startTime, int vaccinationTimes, int patientId, int vaccinationId, int vaccinationStatusId) {
        iVaccinationHistoryRepository.insertVaccinationHTR(deleteFlag, dosage, endTime, guardianName, guardianPhone, startTime, vaccinationTimes, patientId, vaccinationId, vaccinationStatusId);
    }
}
