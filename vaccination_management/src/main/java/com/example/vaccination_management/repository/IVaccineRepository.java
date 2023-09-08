package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {

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
