package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {


    /**
     * TLINH
     * Insert patient information
     */
    @Transactional
    @Modifying
    @Query(value="INSERT INTO patient (name, gender, phone, address, birthday, health_insurance, guardian_name, guardian_phone, detele_flag, account_id)" +
            "VALUE(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)", nativeQuery = true)
    void insertPatient(String name, Boolean gender, String phone, String address, LocalDate birthday, String healthInsurance, String guardianName, String guardianPhone, Boolean deleteFlag, Integer accountId);



    /**
     * TLINH
     * find all patient by detele flag and account id is not null
     */
    @Query(value="SELECT * FROM patient WHERE patient.detele_flag = ?1 AND patient.account_id IS NOT NULL",nativeQuery = true)
    List<Patient> findAllByDeleteFlag (Boolean deleteFlag);


    /**
     * TLINH
     * find all patient by account id is null
     */
        @Query(value = "SELECT * FROM patient WHERE patient.account_id IS NULL", nativeQuery = true)
        List<Patient> fillAllByAccountIDisNull();

    /**
     * TLINH
     * find all patient by detele flag and account id is not null
     */
    @Transactional
    @Modifying
    @Query(value="UPDATE patient SET detele_flag = ?1 WHERE patient.id = ?2",nativeQuery = true)
    void updateDeleteFlagById(Boolean deleteFlag, Integer id);


    /**
       * TLINH
       * Update account id = null in table patient by account id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE patient SET account_id = null WHERE patient.account_id = ?1",nativeQuery = true)
    void updateAccountIdByAccountId(Integer accountId);

    /**
       * TLINH
       * Update name, birthday, gender, phone, guardian name, guardian phone in table patient by patient id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE patient SET name = ?1, birthday = ?2, address = ?3, gender = ?4, phone = ?5, guardian_name = ?6, guardian_phone = ?7 WHERE patient.id = ?8", nativeQuery = true)
    void upPatient(String name, LocalDate birthday, String address, Boolean gender, String phone, String guardianName, String guardianPhone, Integer id);

    
    /**
       * TLINH
       * Count is number health insurance in table patient by health insurance
     */
    @Query(value = "select count(health_insurance) from patient where health_insurance = ?1", nativeQuery = true)
    Integer finByHealthInsurance(String healthInsurance);


    /**
       * TLINH
       * pagination, search by name, phone number, health insurance code with delete flag =? and have account is not null
     */
    @Query(value = "SELECT * FROM patient WHERE (health_insurance LIKE %?1% OR name LIKE %?2% OR phone LIKE %?3%) AND detele_flag = ?4 AND account_id IS NOT NULL ", nativeQuery = true)
    Page<Patient> findAllByHealthOrNameOrPhonePage(String healthInsurance, String name, String phone, Boolean deleteFlag, Pageable pageable);

    
    /**
       * TLINH
       * count the number of patient table ids by health insurance code, name, phone number and have delete flag=? and account id is not null
     */
    @Query(value = "SELECT count(patient.id) FROM patient WHERE (health_insurance LIKE %?1% OR name LIKE %?2% OR phone LIKE %?3%) AND detele_flag = ?4 AND account_id IS NOT NULL", nativeQuery = true)
    long getTotalPatient(String healthInsurance, String name, String phone, Boolean deleteFlag);

    /**
       * TLINH
       * Pagination, search by health insurance code, name, phone number and have account id is null
     */
    @Query(value = "SELECT * FROM patient WHERE (health_insurance LIKE %?1% OR name LIKE %?2% OR phone LIKE %?3%) AND account_id IS NULL",nativeQuery = true)
    Page<Patient> findAllByNameAndNull(String healthInsurance, String name, String phone, Pageable pageable);

    /**
       * TLINH
       * count the number of patient table ids by health insurance code, name, phone number and account id is null
     */
    @Query(value = "SELECT count(patient.id) FROM patient WHERE (health_insurance LIKE %?1% OR name LIKE %?2% OR phone LIKE %?3%) AND account_id IS NULL", nativeQuery = true)
    long getTotalPatientAccountNull(String healthInsurance, String name, String phone);




}


