package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService implements IVaccineService {

    @Autowired
    private IVaccineRepository iVaccineRP;

    @Override
    public List<Vaccine> findAll() {
        return iVaccineRP.findAll();
    }

}
