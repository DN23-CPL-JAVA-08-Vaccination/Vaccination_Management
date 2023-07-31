package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    @Transactional
    @Modifying
    @Query(value="INSERT INTO patient (name, gender, phone, address, birthday, health_insurance, guardian_name, guardian_phone, detele_flag, account_id)" +
            "VALUE(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean deleteFlag, Integer accountId);

        //tìm những patient có delete=? và id_account k null
    @Query(value="SELECT * FROM patient WHERE patient.detele_flag = ?1 AND patient.account_id IS NOT NULL",nativeQuery = true)
    List<Patient> findAllByDeleteFlag (Boolean deleteFlag);

        //Tìm nhưng patient có account_id là null
        @Query(value = "SELECT * FROM patient WHERE patient.account_id IS NULL", nativeQuery = true)
    List<Patient> fillAllByAccountIDisNull();

    @Transactional
    @Modifying
    @Query(value="UPDATE patient SET detele_flag = ?1 WHERE patient.id = ?2",nativeQuery = true)
    void updateDeleteFlagById(Boolean deleteFlag, Integer id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE patient SET account_id = null WHERE patient.account_id = ?1",nativeQuery = true)
    void updateAccountIdByAccountId(Integer accountId);


    @Transactional
    @Modifying
    @Query(value = "UPDATE patient SET name = ?1, birthday = ?2, address = ?3, gender = ?4, phone = ?5, guardian_name = ?6, guardian_phone = ?7 WHERE patient.id = ?8", nativeQuery = true)
    void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id);

    @Query(value = "select count(health_insurance) from patient where health_insurance = ?1", nativeQuery = true)
    Integer finByHealthInsurance(String healthInsurance);

}

