package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    @Transactional
    @Modifying
    @Query(value="INSERT INTO patient (name, gender, phone, address, birthday, health_insurance, guardian_name, guardian_phone, enable_flag, account_id)" +
            "VALUE(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean enableFlag, Integer accountId);

}

