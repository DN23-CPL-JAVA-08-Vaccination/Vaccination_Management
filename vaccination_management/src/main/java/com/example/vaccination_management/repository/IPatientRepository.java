package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.PatientInforDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    /**
     * ThangLV
     * get Information of Patient
     */
    String SELECT_PATIENT_BY_ID = "select p.id, p.name, p.address, p.birthday, p.gender, p.health_insurance as healthInsurance, p.phone, p.guardian_name as guardianName, p.guardian_phone as guardianPhone, a.email, a.username from patient  p\n" +
            "join account a on a.id = p.account_id\n" +
            "where  p.enable_flag = 1 and p.id = ?;";

    @Query(value = SELECT_PATIENT_BY_ID, countQuery = SELECT_PATIENT_BY_ID, nativeQuery = true)
    PatientInforDTO getInforById(int index);
}
