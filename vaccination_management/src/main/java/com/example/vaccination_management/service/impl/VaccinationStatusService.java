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


    @Autowired
    public VaccinationStatusService(IVaccinationStatusRepository iVaccinationStatusRepository) {
        this.iVaccinationStatusRepository = iVaccinationStatusRepository;
    }

    @Override
    public VaccinationStatus findById(int id) {
        return iVaccinationStatusRepository.findById(id).orElse(null);
    }

    @Override
    public List<VaccinationStatus> finAll() {
        return iVaccinationStatusRepository.findAll();
    }


    /**
     * LoanHTP
     * Retrieves a list of all available vaccination status records.
     */
    @Override
    public List<VaccinationStatus> getAllVaccinationStatus() {
        return iVaccinationStatusRepository.findAll();
    }
}
