package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.InforPatientDTO;
import com.example.vaccination_management.dto.IPatientDTO;
import com.example.vaccination_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    @Override
    Patient getById(Integer integer);

    /**
     * LoanHTP
     * Retrieves a patient's information based on the provided patient ID.
     */
    Patient findPatientById(int id);
}
