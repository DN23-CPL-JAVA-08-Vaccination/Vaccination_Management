package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineTypeService implements IVaccineTypeService {

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    /**
     * LoanHTP
     * Retrieves a list of all available vaccine types.
     */
    @Override
    public List<VaccineType> showVaccineType() {
        return iVaccineTypeRepository.findAll();
    }

    /**
     * LoanHTP
     * Retrieves a vaccine type record based on the provided ID.
     */
    @Override
    public VaccineType findVaccineTypeById(int id) {
        return iVaccineTypeRepository.findById(id).orElse(null);
    }
}
