package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;

@Repository
public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {


    /**
     * HuyLVN
     * query vaccines information of the same type of vaccine from the database
     */
    List<Vaccine> getVaccineByVaccineType(VaccineType vaccineType);

    /**
     * HuyLVN
     * query the vaccine information that has the deleteFlag field as false from the database
     */
    List<Vaccine> findByDeleteFlagFalse();

    /**
     * HuyLVN
     * query the vaccine information that has the deleteFlag field as true from the database
     */
    List<Vaccine> findByDeleteFlagTrue();

    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int vaccineID);

    /**
     * QuangVT
     * get all vaccine
     */
    @Query(
            value = "SELECT vac.id as id, vac.code, vac.name as vaccineName,  vac.description , vac.duration, " +
                    "vac.price, vac.create_date as createDate, typ.name as typeName\n" +
                    "FROM vaccine vac\n" +
                    "JOIN vaccine_type typ ON  vac.vaccine_type_id = typ.id\n" +
                    "WHERE vac.delete_flag = 0 " +
                    "ORDER BY vac.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccine vac WHERE vac.delete_flag = 0",
            nativeQuery = true
    )
    Page<IVaccineDTO> getAllVaccine(Pageable pageable);

    /**
     * QuangVT
     * get vaccine by type
     */
    @Query(
            value = "SELECT vac.id as id, vac.code, vac.name as vaccineName,  vac.description , vac.duration, " +
                    "vac.price, vac.create_date as createDate, typ.name as typeName\n" +
                    "FROM vaccine vac\n" +
                    "JOIN vaccine_type typ ON  vac.vaccine_type_id = typ.id\n" +
                    "WHERE vac.delete_flag = 0 AND vac.vaccine_type_id = :type " +
                    "ORDER BY vac.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccine vac WHERE vac.delete_flag = 0 AND vac.vaccine_type_id = :type",
            nativeQuery = true
    )
    Page<IVaccineDTO> getVaccineByType(Pageable pageable, @Param("type") Integer type);

    /**
     * QuangVT
     * search vaccine
     */
    @Query(
            value = "SELECT vac.id as id, vac.code, vac.name as vaccineName,  vac.description , vac.duration, \n" +
                    "vac.price, vac.create_date as createDate, typ.name as typeName\n" +
                    "FROM vaccine vac\n" +
                    "JOIN vaccine_type typ ON  vac.vaccine_type_id = typ.id\n" +
                    "WHERE vac.delete_flag = 0 AND (vac.name LIKE ?1 OR vac.code LIKE ?1  )\n" +
                    "ORDER BY vac.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccine vac WHERE vac.delete_flag = 0 AND (vac.name LIKE ?1 OR vac.code LIKE ?1 )",
            nativeQuery = true
    )
    Page<IVaccineDTO> searchVaccine(String strSearch, Pageable pageable);


//    Vaccine findById(int id);

//    List<Vaccine> findVaccineByVaccineType(VaccineType VaccineType);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided vaccine type.
     */
    Page<Vaccine> findByVaccineTypeAndDeleteFlagFalse(VaccineType vaccineType, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines associated with the specified vaccine type.
     */
    long countByVaccineTypeAndDeleteFlagFalse(VaccineType vaccineType);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines with names containing the provided search query (case-insensitive).
     */
    @Query("SELECT v FROM Vaccine v WHERE v.name LIKE %:name% AND v.deleteFlag = false")
    Page<Vaccine> findByNameAndDeleteFlagFalseContainingIgnoreCase(@Param("name") String searchQuery, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines with names containing the provided search query (case-insensitive).
     */
    @Query("SELECT COUNT(v) FROM Vaccine v WHERE LOWER(v.name) LIKE LOWER(CONCAT('%', :name, '%')) AND v.deleteFlag = false")
    long countByNameAndDeleteFlagFalseContainingIgnoreCase(@Param("name") String searchQuery);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided vaccine type and containing the search query in the name (case-insensitive).
     */
    @Query("SELECT v FROM Vaccine v WHERE v.vaccineType = :vaccineType AND LOWER(v.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')) AND v.deleteFlag = false")
    Page<Vaccine> findByVaccineTypeAndNameAndDeleteFlagFalseContainingIgnoreCase(@Param("vaccineType") VaccineType vaccineType, @Param("searchQuery") String searchQuery, Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines associated with the specified vaccine type and containing the search query in the name (case-insensitive).
     */
    @Query("SELECT COUNT(v) FROM Vaccine v WHERE v.vaccineType = :vaccineType AND LOWER(v.name) LIKE LOWER(CONCAT('%', :searchQuery, '%')) AND v.deleteFlag = false")
    long countByVaccineTypeAndNameAndDeleteFlagFalseContainingIgnoreCase(@Param("vaccineType") VaccineType vaccineType, @Param("searchQuery")  String searchQuery);

    /**
     * LoanHTP
     * Retrieves a pageable list of vaccines that have not been marked for deletion.
     */
    Page<Vaccine> findByDeleteFlagFalse(Pageable pageable);

    /**
     * LoanHTP
     * Counts the number of vaccines associated with the specified vaccine type
     * and containing the search query in the name (case-insensitive).
     */
    @Query("SELECT COUNT(v) FROM Vaccine v WHERE v.deleteFlag = false")
    long countByNameAndDeleteFlagFalseContainingIgnoreCase();
}
