package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccinationStatus;

import java.util.List;

public interface IVaccinationStatusService {

    /**
     * LoanHTP
     * Retrieves a list of all available vaccination status records.
     */
    List<VaccinationStatus> getAllVaccinationStatus();
}
