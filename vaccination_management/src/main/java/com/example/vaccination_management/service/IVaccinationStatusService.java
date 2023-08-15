package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccinationStatus;

import java.util.List;

public interface IVaccinationStatusService {

    public VaccinationStatus findById(int id);
    List<VaccinationStatus> finAll();
    /**
     * LoanHTP
     * Retrieves a list of all available vaccination status records.
     */
    List<VaccinationStatus> getAllVaccinationStatus();
}
