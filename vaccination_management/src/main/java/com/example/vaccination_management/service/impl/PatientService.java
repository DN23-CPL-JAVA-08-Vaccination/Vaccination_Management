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
        return iPatientRepository.getPatients(strSearch,pageable);
    }
    @Override
    public Patient getPatientById(Integer id){
        return iPatientRepository.getById(id);
    }
}
