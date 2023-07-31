package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Patient;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService implements IPatientService {
    @Autowired
    private IPatientRepository iPatientRP;


    @Override
    public void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId) {
        iPatientRP.insertPatient(name, gender, phone, address, birthday, healthInsurance, guardianName, guardianPhone, enableFlag, accountId);
    }



    @Override
    public List<Patient> findAllByDeleteFlag(Boolean deleteFlag) {
        return iPatientRP.findAllByDeleteFlag(deleteFlag);
    }


    @Override
    public List<Patient> fillAllByAccountIDisNull() {
        return iPatientRP.fillAllByAccountIDisNull();
    }

    @Override
    public void updateDeleteFlagById(Boolean deleteFlag, Integer id) {
        iPatientRP.updateDeleteFlagById(deleteFlag, id);
    }


    @Override
    public Optional<Patient> findById(Integer integer) {
        return iPatientRP.findById(integer);
    }




    @Override
    public void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id) {
        iPatientRP.upPatient(name, birthday, address, gender, phone, guardianName, guardianPhone, id);
    }


    @Override
    public void updateAccountIdByAccountId(Integer accountId) {
        iPatientRP.updateAccountIdByAccountId(accountId);
    }


    @Override
    public Integer finByHealthInsurance(String healthInsurance) {
        return iPatientRP.finByHealthInsurance(healthInsurance);
    }
}

