package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.PatientByUsernameDTO;
import com.example.vaccination_management.entity.Account;
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

    /**
     * ThangLe
     */
    @Override
    public Patient findPatientByUsername(String username) {
        PatientByUsernameDTO patientByUsernameDTO = iPatientRepository.getPatientByUsername(username);
        Patient patient = new Patient();
        patient.setId(patientByUsernameDTO.getId());
        patient.setName(patientByUsernameDTO.getName());
        patient.setHealthInsurance(patientByUsernameDTO.getHealthInsurance());
        patient.setGender(patientByUsernameDTO.getGender());
        patient.setAddress(patientByUsernameDTO.getAddress());
        patient.setPhoneNumber(patientByUsernameDTO.getPhone());
        patient.setBirthday(patientByUsernameDTO.getBirthday());
        patient.setGuardianName(patientByUsernameDTO.getGuardianName());
        patient.setGuardianPhone(patientByUsernameDTO.getGuardianPhone());
//        patient.setDeleteFlag(patientByUsernameDTO.getDeleteFlag());

        Account account = new Account();
        account.setId(patientByUsernameDTO.getAccountId());
        account.setEmail(patientByUsernameDTO.getEmail());
        account.setUserName(patientByUsernameDTO.getUsername());
//        account.setEnableFlag(patientByUsernameDTO.getEnableFlag());

        patient.setAccount(account);
        return patient;
    }
}
