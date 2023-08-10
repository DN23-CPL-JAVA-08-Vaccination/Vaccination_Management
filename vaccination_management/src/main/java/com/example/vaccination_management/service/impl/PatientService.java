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

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    @Override
    public Patient findPatientById(int id) {
        return iPatientRepository.findPatientById(id);
    }

    /**
     * LoanHTP
     * Retrieves a list of patients to display.
     */
    @Override
    public List<Patient> showPatient() {
        return iPatientRepository.findAll();
    }
}
