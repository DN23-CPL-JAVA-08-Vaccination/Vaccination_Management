package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPatientService {

    /**
     * ThangLV
     * get all information of Patient
     */
    InforPatientDTO getInforByUsername(String username);

    Page<IPatientDTO> getPatients(Pageable pageable, String strSearch);


    Patient getPatientById(Integer id);

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    Patient findPatientById(int id);

    /**
     * LoanHTP
     * Retrieves a list of patients to display.
     */
    List<Patient> showPatient();

}
