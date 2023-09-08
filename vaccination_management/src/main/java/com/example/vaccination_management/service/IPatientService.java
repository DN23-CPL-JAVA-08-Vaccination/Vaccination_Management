package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Patient;

import java.util.List;

public interface IPatientService {

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

    /**
     * ThangLV
     * Find Patient Bay username
     */
    Patient findPatientByUsername(String username);
}
