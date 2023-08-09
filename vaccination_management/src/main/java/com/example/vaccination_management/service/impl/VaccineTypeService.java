package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VaccineTypeService implements IVaccineTypeService {
    @Autowired
    IVaccineTypeRepository iVaccineTypeRepository;

    /**
     * QuangVT
     * get all vaccine type
     */
    @Override
    public Page<VaccineType> findAllVaccine(String strSearch, Pageable pageable) {
        return iVaccineTypeRepository.getAllVaccineType(strSearch,pageable);
    }
    @Override
    public <S extends VaccineType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return iVaccineTypeRepository.findAll(example, pageable);
    }
}
