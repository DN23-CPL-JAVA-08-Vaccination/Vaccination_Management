package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {

    /**
     * HuyLVN
     * count the number of occurrences of ID in the database
     */
    Long countById(int vaccineTypeID);

    /**
     * QuangVT
     * get all vaccine type
     */
    @Query(
            value = "SELECT vac.id as id,  vac.name as name, vac.delete_flag " +
                    "FROM vaccine_type vac \n" +
                    "WHERE (vac.name LIKE ?1 OR vac.id LIKE ?1 ) AND vac.delete_flag = 0  \n" +
                    "ORDER BY vac.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccine_type vac WHERE (vac.name LIKE ?1 OR vac.id LIKE ?1) AND vac.delete_flag = 0 ",
            nativeQuery = true
    )
    Page<VaccineType> getAllVaccineType(String strSearch, Pageable pageable);

    /**
     * HuyLVN
     * query the vaccine type information that has the deleteFlag field as false from the database
     */
    List<VaccineType> findByDeleteFlagFalse();

    /**
     * HuyLVN
     * query the vaccine type information that has the deleteFlag field as true from the database
     */
    List<VaccineType> findByDeleteFlagTrue();

    @Query("SELECT vt FROM VaccineType vt WHERE vt.deleteFlag = false")
    List<VaccineType> findByDeleteFlagsFalse();

}
