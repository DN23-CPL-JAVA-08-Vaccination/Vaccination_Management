package com.example.vaccination_management.repository;


import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.dto.IPatientDTO;

import com.example.vaccination_management.dto.PatientByUsernameDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Integer> {

    /**
     * QuangVT
     * get list  Patient vaccine
     */
    @Query(
            value = "SELECT pa.id as id, pa.name, pa.health_insurance as healthInsurance,  pa.gender, pa.address , pa.phone, pa.birthday, \n" +
                    "pa.guardian_name as guardianName, pa.guardian_phone as guardianPhone\n" +
                    "FROM patient pa\n" +
                    "WHERE pa.detele_flag = 0 AND (pa.name LIKE ?1 OR pa.birthday LIKE ?1 OR pa.phone LIKE ?1 )\n" +
                    "ORDER BY pa.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.patient pa WHERE pa.detele_flag = 0 AND (pa.name LIKE ?1 OR pa.birthday LIKE ?1 OR pa.phone LIKE ?1 )",
            nativeQuery = true
    )
    Page<IPatientDTO> getPatients(String strSearch, Pageable pageable);

    /**
     * ThangLV
     * get Information of Patient
     */
    String SELECT_PATIENT_BY_ID = "select p.id, p.name, p.address, p.birthday, p.gender, p.health_insurance as healthInsurance, p.phone, p.guardian_name as guardianName, p.guardian_phone as guardianPhone, a.email, a.username from patient  p\n" +
            "            join account a on a.id = p.account_id\n" +
            "            where  p.detele_flag = 0 and a.username = ?";

    @Query(value = SELECT_PATIENT_BY_ID, countQuery = SELECT_PATIENT_BY_ID, nativeQuery = true)
    InforPatientDTO getInforByUsername(String username);

    /**
     * ThangLV
     * get Infor Patient by Username
     */
    String SELECT_PATIENT_BY_USERNAME = "select p.id, p.name, p.phone, p.address, p.birthday,p.gender,p.detele_flag, p.health_insurance as healthInsurance,p.guardian_name,p.guardian_phone, a.id as accountId, a.email,a.username, a.enable_flag from patient p\n" +
            "    join account a on a.id = p.account_id\n" +
            "    where username = ?1";
    @Query(value = SELECT_PATIENT_BY_USERNAME, countQuery = SELECT_PATIENT_BY_USERNAME, nativeQuery = true)
    PatientByUsernameDTO getPatientByUsername(String username);



    Patient getPatientById(Integer Id);

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    Patient findPatientById(int id);

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


