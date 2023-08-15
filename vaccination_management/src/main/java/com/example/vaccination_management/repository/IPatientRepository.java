package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.PatientByUsernameDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    Patient findPatientById(int id);

    /**
     * ThangLV
     * get Infor Patient by Username
     */
    String SELECT_PATIENT_BY_USERNAME = "select p.id, p.name, p.phone, p.address, p.birthday,p.gender,p.delete_flag, p.health_insurance,p.guardian_name,p.guardian_phone, a.id as accountId, a.email,a.username, a.enable_flag from patient p\n" +
            "    join account a on a.id = p.account_id\n" +
            "    where username = ?1";
    @Query(value = SELECT_PATIENT_BY_USERNAME, countQuery = SELECT_PATIENT_BY_USERNAME, nativeQuery = true)
    PatientByUsernameDTO getPatientByUsername(String username);
}
