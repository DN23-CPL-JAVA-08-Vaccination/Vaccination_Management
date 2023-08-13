package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.IVaccinationHistoryDTO;
import com.example.vaccination_management.entity.VaccinationHistory;

import com.example.vaccination_management.entity.Patient;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import javax.transaction.Transactional;

import java.util.List;

@Repository
public interface IVaccinationHistoryRepository extends JpaRepository<VaccinationHistory, Integer> {

    VaccinationHistory findById(int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE vaccination_history SET vaccination_status_id = ?1 WHERE vaccination_history.id = ?2",nativeQuery = true)
    void updateStatusVaccinationHistory(Integer statusId, Integer vaccinationHR);

    /**
     * QuangVT
     * get vaccination schedule
     */
    @Query(
            value = "SELECT his.id as id, pa.name as patientName, pa.birthday as patientBirth, vac.description as vaccinationDesc, vaccine.name as vaccineName, his.start_time as regisTime, his.vaccination_times as vaccinationTimes " +
                    "FROM vaccination_manager.vaccination_history his " +
                    "JOIN patient pa ON his.patient_id = pa.id " +
                    "JOIN vaccination vac ON his.vaccination_id = vac.id " +
                    "JOIN vaccine ON vac.vaccine_id = vaccine.id " +
                    "WHERE his.vaccination_status_id = 1 AND his.delete_flag = 0  AND  DATE(his.start_time) = CURDATE() AND (pa.name LIKE ?1 OR pa.birthday LIKE ?1) " +
                    "ORDER BY his.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccination_history his JOIN patient pa ON his.patient_id = pa.id WHERE  his.vaccination_status_id = 1 AND his.delete_flag = 0  AND  DATE(his.start_time) = CURDATE() AND (pa.name LIKE ?1 OR pa.birthday )",
            nativeQuery = true
    )
    Page<IVaccinationHistoryDTO> getVaccinationSchedule( String strSearch, Pageable pageable);
    /**
     * QuangVT
     * get vaccination history completed
     */
    @Query(
            value = "SELECT his.id as id, pa.name as patientName, pa.birthday as patientBirth, vac.description as vaccinationDesc, vaccine.name as vaccineName, his.start_time as regisTime, his.end_time as lastTime, his.vaccination_times as vaccinationTimes " +
                    "FROM vaccination_manager.vaccination_history his " +
                    "JOIN patient pa ON his.patient_id = pa.id " +
                    "JOIN vaccination vac ON his.vaccination_id = vac.id " +
                    "JOIN vaccine ON vac.vaccine_id = vaccine.id " +
                    "WHERE his.vaccination_status_id = 2 AND his.delete_flag = 0 AND (pa.name LIKE ?1 OR pa.birthday LIKE ?1) " +
                    "ORDER BY his.end_time DESC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccination_history his JOIN patient pa ON his.patient_id = pa.id WHERE his.vaccination_status_id = 2 AND his.delete_flag = 0 AND (pa.name LIKE ?1 OR pa.birthday LIKE ?1 )",
            nativeQuery = true
    )
    Page<IVaccinationHistoryDTO> getHistoryVaccination(String strSearch,Pageable pageable);
    /**
     * QuangVT
     * get  details of vaccination history by id
     */
    @Query(
            value = "SELECT his.id,  pa.name as patientName, pa.birthday as patientBirth, vac.description as vaccinationDesc, vaccine.name as vaccineName ,\n" +
                    "his.start_time as regisTime ,  his.end_time as lastTime, his.vaccination_times as vaccinationTimes,\n" +
                    "emp.name as employeeName , emp.phone as employeePhone , stt.id as status, his.guardian_name as guardianName, his.guardian_phone as guardianPhone, \n" +
                    "his.pre_status as preStatus , his.dosage,vac.duration, TIMESTAMPDIFF(YEAR, pa.birthday, CURDATE()) AS agePatient\n" +
                    "FROM vaccination_manager.vaccination_history his\n" +
                    "JOIN employee emp ON his.employee_id = emp.id\n" +
                    "JOIN patient pa ON his.patient_id = pa.id\n" +
                    "JOIN vaccination vac ON his.vaccination_id = vac.id\n" +
                    "JOIN vaccine ON vac.vaccine_id = vaccine.id\n" +
                    "JOIN vaccination_status  stt ON his.vaccination_status_id = stt.id\n" +
                    "WHERE his.id = :id ;",
            nativeQuery = true
    )
    IVaccinationHistoryDTO getVaccinationHistoryByID(@Param("id") Integer id);
    /**
     * QuangVT
     * count vaccination today
     */
    @Query(
            value = "SELECT \n" +
                    "    COUNT(*) AS allSchedule, \n" +
                    "    SUM(CASE WHEN his.vaccination_status_id= 2 THEN 1 ELSE 0 END) AS completeSchedule \n" +
                    "FROM vaccination_history his \n" +
                    "WHERE DATE(his.start_time) = CURDATE() ;",
            nativeQuery = true
    )
    IVaccinationHistoryDTO countVaccination();
    /**
     * QuangVT
     * get data for chart
     */
    @Query(
            value = "SELECT  COUNT(vaccination_history.id) AS count\n" +
                    "FROM \n" +
                    " (\n" +
                    "  SELECT 1 AS month\n" +
                    "  UNION SELECT 2 AS month\n" +
                    "  UNION SELECT 3 AS month\n" +
                    "  UNION SELECT 4 AS month\n" +
                    "  UNION SELECT 5 AS month\n" +
                    "  UNION SELECT 6 AS month\n" +
                    "  UNION SELECT 7 AS month\n" +
                    "  UNION SELECT 8 AS month\n" +
                    "  UNION SELECT 9 AS month\n" +
                    "  UNION SELECT 10 AS month\n" +
                    "  UNION SELECT 11 AS month\n" +
                    "  UNION SELECT 12 AS month\n" +
                    ") AS months\n" +
                    "LEFT JOIN vaccination_history ON  MONTH(vaccination_history.end_time) = months.month\n" +
                    "AND YEAR(vaccination_history.end_time) = :year \n" +
                    "AND vaccination_history.vaccination_status_id = 2\n" +
                    "GROUP BY  months.month;",
            nativeQuery = true
    )
    List<Integer> getDataForChart(@Param("year") String year);

    /**
     * QuangVT
     * get vaccination history by patient
     */
    @Query(
            value = "SELECT his.id as id, pa.name as patientName, pa.birthday as patientBirth, vac.description as vaccinationDesc, vaccine.name as vaccineName, his.start_time as regisTime, his.end_time as lastTime, his.vaccination_times as vaccinationTimes, his.vaccination_status_id as status " +
                    "FROM vaccination_manager.vaccination_history his " +
                    "JOIN patient pa ON his.patient_id = pa.id " +
                    "JOIN vaccination vac ON his.vaccination_id = vac.id " +
                    "JOIN vaccine ON vac.vaccine_id = vaccine.id " +
                    "WHERE  his.delete_flag = 0 AND  his.patient_id = ?1 "+
                    "ORDER BY his.end_time DESC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccination_history his JOIN patient pa ON his.patient_id = pa.id WHERE  his.delete_flag = 0 AND  his.patient_id = ?1 ",
            nativeQuery = true
    )
    Page<IVaccinationHistoryDTO> getVaccinationByPatient(Integer id,Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a list of vaccination history records associated with the provided patient.
     */
    List<VaccinationHistory> findVaccinationHistoriesByPatient(Patient patient);

    /**
     * LoanHTP
     * Counts the number of vaccination history records associated with the provided patient.
     */
    long countVaccinationHistoriesByPatient(Patient patient);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient.
     */
    Page<VaccinationHistory> findByPatient(Patient patient, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided vaccination status ID and patient.
     */
    Page<VaccinationHistory> findByVaccinationStatusIdAndPatient(int vaccinationStatusId, Patient patient, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient ID and optionally a vaccination status ID.
     */
    @Query("SELECT vh FROM VaccinationHistory vh WHERE vh.patient.id = :patientId AND (:vaccinationStatusId IS NULL OR vh.vaccinationStatus.id = :vaccinationStatusId)")
    public Page<VaccinationHistory> listVaccinationHistoryByPatientAndStatusPaged(@Param("patientId") int patientId, @Param("vaccinationStatusId") Integer vaccinationStatusId, Pageable pageable);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccination history records associated with the provided patient ID.
     */
    public Page<VaccinationHistory> findByPatientId(int patientId, Pageable pageable);
}
