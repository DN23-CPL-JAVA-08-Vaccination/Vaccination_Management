package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPatientService {

    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId);


    List<Patient> findAllByDeleteFlag(Boolean deleteFlag);


    List<Patient> fillAllByAccountIDisNull();

    void updateDeleteFlagById(Boolean deleteFlag, Integer id);


    Optional<Patient> findById(Integer integer);



    void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id);

    void updateAccountIdByAccountId(Integer accountId);

    Integer finByHealthInsurance(String healthInsurance);
}
