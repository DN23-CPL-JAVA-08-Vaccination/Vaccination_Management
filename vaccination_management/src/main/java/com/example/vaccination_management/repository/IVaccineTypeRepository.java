package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {
    /**
     * QuangVT
     * get all vaccine type
     */
    @Query(
            value = "SELECT vac.id as id,  vac.name as name " +
                    "FROM vaccine_type vac \n" +
                    "WHERE vac.name LIKE ?1 OR vac.id LIKE ?1 \n" +
                    "ORDER BY vac.id ASC ",
            countQuery = "SELECT COUNT(*) FROM vaccination_manager.vaccine_type vac WHERE (vac.name LIKE ?1 OR vac.id LIKE ?1) ",
            nativeQuery = true
    )
    Page<VaccineType> getAllVaccineType(String strSearch, Pageable pageable);



}
