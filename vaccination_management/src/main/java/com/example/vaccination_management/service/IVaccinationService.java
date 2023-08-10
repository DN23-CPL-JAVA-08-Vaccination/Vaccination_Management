package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.IVaccinationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVaccinationService {
    Page<IVaccinationDTO> getAllVaccination(String strSearch, Pageable pageable);
}
