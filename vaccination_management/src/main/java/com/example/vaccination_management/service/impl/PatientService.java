package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository iPatientRepository;
    @Override
    public Page<IPatientDTO> getPatients(Pageable pageable, String strSearch) {
        return iPatientRepository.getPatients(strSearch,pageable);
    }

    @Override
    public Patient getPatientById(Integer id){
        return iPatientRepository.getById(id);
    }
}
