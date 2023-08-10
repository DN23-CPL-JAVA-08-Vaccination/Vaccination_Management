package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccinationStatus;
import com.example.vaccination_management.repository.IVaccinationStatusRepository;
import com.example.vaccination_management.service.IVaccineStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineStatusService implements IVaccineStatusService {
    @Autowired
    private IVaccinationStatusRepository iVaccinationStatusRepository;
    /**
     * QuangVT
     * get vaccination status
     * */
    @Override
    public VaccinationStatus getById(Integer integer) {
        return iVaccinationStatusRepository.getById(integer);
    }
}
