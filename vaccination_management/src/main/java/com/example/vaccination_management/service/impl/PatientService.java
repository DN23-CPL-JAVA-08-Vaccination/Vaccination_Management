package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.PatientInforDTO;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {

    @Autowired
    IPatientRepository patientRepository;

    /**
     * ThangLV
     * get all information of Patient
     */
    @Override
    public PatientInforDTO getInforById(int i) {
        return patientRepository.getInforById(i);
    }
}
