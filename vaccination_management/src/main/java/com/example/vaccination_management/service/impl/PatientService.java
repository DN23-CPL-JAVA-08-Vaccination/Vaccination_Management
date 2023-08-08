package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientService implements IPatientService {

    @Autowired
    private IPatientRepository iPatientRepository;
    @Override
    public List<Patient> finAll() {
        return iPatientRepository.findAll();
    }
}
