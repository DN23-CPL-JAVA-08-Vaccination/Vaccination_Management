package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.repository.IVaccinationRepository;
import com.example.vaccination_management.service.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaccinationService implements IVaccinationService {
    @Autowired
    private IVaccinationRepository iVaccinationRepository;

    @Override
    public Page<IVaccinationDTO> getAllVaccination(String strSearch, Pageable pageable) {
        return iVaccinationRepository.getVaccinations(strSearch,pageable);
    }
    /**
     * QuangVT
     * search  vaccine
     */
}
