package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaccineService implements IVaccineService {
    @Autowired
    private IVaccineRepository iVaccineRepository;
    /**
     * QuangVT
     * get all vaccine
     */
    @Override
    public Page<IVaccineDTO> findAllVaccine(Pageable pageable) {
        return iVaccineRepository.getAllVaccine(pageable);
    }
    /**
     * QuangVT
     * search  vaccine
     */
    @Override
    public Page<IVaccineDTO> searchVaccine(Pageable pageable, String strSearch) {
        return iVaccineRepository.searchVaccine(strSearch ,pageable);
    }
    /**
     * QuangVT
     * get vaccine by type
     */
    @Override
    public Page<IVaccineDTO> getVaccineByType(Pageable pageable, Integer type) {
        return iVaccineRepository.getVaccineByType(pageable,type);
    }



    @Override
    public long count() {
        return iVaccineRepository.count();
    }

    @Override
    public <S extends Vaccine> S save(S entity) {
        return iVaccineRepository.save(entity);
    }
}
