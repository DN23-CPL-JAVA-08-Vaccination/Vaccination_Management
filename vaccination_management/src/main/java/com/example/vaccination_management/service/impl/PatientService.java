package com.example.vaccination_management.service.impl;


import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.dto.PatientByUsernameDTO;
import com.example.vaccination_management.entity.Account;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.vaccination_management.dto.IPatientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class PatientService implements IPatientService {

    @Autowired
    private IPatientRepository iPatientRepository;


    @Override
    public List<Patient> finAll() {
        return iPatientRepository.findAll();
    }

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

    /**
     * ThangLV
     * get Infor Patient by Username
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

    /**
     * TLINH
     * insert patient information
     */
    @Override
    public void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId) {
        iPatientRepository.insertPatient(name, gender, phone, address, birthday, healthInsurance, guardianName, guardianPhone, enableFlag, accountId);
    }


    /**
     * TLINH
     * find all patient by account is null
     */
    @Override
    public List<Patient> fillAllByAccountIDisNull() {
        return iPatientRepository.fillAllByAccountIDisNull();
    }


    /**
     * TLINH
     * update delete flag by id
     */
    @Override
    public void updateDeleteFlagById(Boolean deleteFlag, Integer id) {
        iPatientRepository.updateDeleteFlagById(deleteFlag, id);
    }


    /**
     * TLINH
     * find patient by id
     */
    @Override
    public Optional<Patient> findById(Integer integer) {
        return iPatientRepository.findById(integer);
    }


    /**
     * TLINH
     * edit patient
     */
    @Override
    public void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id) {
        iPatientRepository.upPatient(name, birthday, address, gender, phone, guardianName, guardianPhone, id);
    }


    /**
     * TLINH
     * count is the number health insurance
     */
    @Override
    public Integer finByHealthInsurance(String healthInsurance) {
        return iPatientRepository.finByHealthInsurance(healthInsurance);

    }


    /**
     * TLINH
     * find all patientby delete flag=?
     */
    @Override
    public List<Patient> findAllByDeleteFlag(Boolean deleteFlag) {
        return iPatientRepository.findAllByDeleteFlag(deleteFlag);
    }


    /**
     * TLINH
     * search and pagination have deleflag =?
     */
    @Override
    public List<Patient> getPatientByPage(String healthInsurance, String name, String phone, Boolean deleteFlag, Pageable pageable) {
        Page<Patient> patientPage = iPatientRepository.findAllByHealthOrNameOrPhonePage(healthInsurance, name, phone, deleteFlag, pageable);

        return patientPage.getContent();
    }


    /**
     * TLINH
     * count total patient
     */
    @Override
    public long getTotalPatient(String healthInsurance, String name, String phone, Boolean deleteFlag) {
        return iPatientRepository.getTotalPatient(healthInsurance, name, phone, deleteFlag);

    }


    /**
     * TLINH
     * search and pagination
     */
    @Override
    public List<Patient> getPatientByPageAccountNull(String healthInsurance, String name, String phone, Pageable pageable) {
        Page<Patient> patientPage = iPatientRepository.findAllByNameAndNull(healthInsurance, name, phone, pageable);
        return patientPage.getContent();
    }


    /**
     * TLINH
     * count total patient
     */
    @Override
    public long getTotalPatientAccountNull(String healthInsurance, String name, String phone) {
        return iPatientRepository.getTotalPatientAccountNull(healthInsurance, name, phone);

    }


    @Override
    public  long countAllPatient(){
        return iPatientRepository.countPatient();
    }

}


