package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.InforPatientDTO;

public interface IPatientService {

    /**
     * ThangLV
     * get all information of Patient
     */
    InforPatientDTO getInforByUsername(String username);
}
