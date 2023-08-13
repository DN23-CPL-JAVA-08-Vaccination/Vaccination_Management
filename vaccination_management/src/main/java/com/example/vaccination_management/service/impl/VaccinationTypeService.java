package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccinationType;
import com.example.vaccination_management.repository.IVaccinationTypeRepository;
import com.example.vaccination_management.service.IVaccinationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationTypeService implements IVaccinationTypeService {

    @Autowired
    private IVaccinationTypeRepository iVaccinationTypeRepository;
     /**
     * VuongLV
     * get all information of VaccinationType, admin after login
     */
    @Override
    public List<VaccinationType> finAll() {

        return iVaccinationTypeRepository.findAll();
    }
}
