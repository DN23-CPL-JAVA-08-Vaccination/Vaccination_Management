package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPatientService {

    /**
     * ThangLV
     * get all information of Patient
     */
    InforPatientDTO getInforByUsername(String username);

    Page<IPatientDTO> getPatients(Pageable pageable, String strSearch);


    Patient getPatientById(Integer id);

}
