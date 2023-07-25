package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.dto.PatientInforDTO;

public interface IPatientService {

    /**
     * ThangLV
     * get all information of Patient
     */
    PatientInforDTO getInforById(int i);
}
