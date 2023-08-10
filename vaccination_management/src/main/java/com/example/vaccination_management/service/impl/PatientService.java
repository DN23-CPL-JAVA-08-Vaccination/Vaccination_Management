package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class PatientService implements IPatientService {

    @Autowired
    private IPatientRepository iPatientRepository;

    /**
     * ThangLV
     * get all information of Patient
     */
    @Override
    public InforPatientDTO getInforByUsername(String username) {
        return iPatientRepository.getInforByUsername(username);
    }

    @Override
    public Page<IPatientDTO> getPatients(Pageable pageable, String strSearch) {
        return iPatientRepository.getPatients(strSearch, pageable);
    }

    @Override
    public Patient getPatientById(Integer id) {
        return iPatientRepository.getById(id);
    }

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
