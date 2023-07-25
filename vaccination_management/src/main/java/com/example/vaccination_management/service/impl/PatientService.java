package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository iPatient;


    @Override
    public void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId) {
        iPatient.insertPatient(name, gender, phone, address, birthday, healthInsurance, guardianName, guardianPhone, enableFlag, accountId);
    }
}
