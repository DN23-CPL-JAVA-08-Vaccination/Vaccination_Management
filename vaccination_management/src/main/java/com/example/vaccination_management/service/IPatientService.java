package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IPatientService {

    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId);


//    List<Patient> findAllByDeleteFlag(Boolean deleteFlag);



    List<Patient> fillAllByAccountIDisNull();

    void updateDeleteFlagById(Boolean deleteFlag, Integer id);


    Optional<Patient> findById(Integer integer);



    void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id);


    Integer finByHealthInsurance(String healthInsurance);
    List<Patient> findAllByDeleteFlag(Boolean deleteFlag);

    List<Patient>getPatientByPage(String healthInsurance, String name, String phone, Boolean deleteFlag, Pageable pageable);

    long getTotalPatient(String healthInsurance, String name, String phone, Boolean deleteFlag);

    List<Patient> getPatientByPageAccountNull(String healthInsurance, String name, String phone, Pageable pageable);

    long getTotalPatientAccountNull(String healthInsurance, String name, String phone);
}
