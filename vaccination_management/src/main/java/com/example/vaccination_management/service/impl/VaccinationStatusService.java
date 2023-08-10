package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.repository.IVaccinationStatusRepository;
import com.example.vaccination_management.service.IVaccinationStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationStatusService implements IVaccinationStatusService {

    @Autowired
    private IVaccinationStatusRepository iVaccinationStatusRepository;

    /**
     * LoanHTP
     * Retrieves a list of all available vaccination status records.
     */
    @Override
    public List<VaccinationStatus> getAllVaccinationStatus() {
        return iVaccinationStatusRepository.findAll();
    }
}
