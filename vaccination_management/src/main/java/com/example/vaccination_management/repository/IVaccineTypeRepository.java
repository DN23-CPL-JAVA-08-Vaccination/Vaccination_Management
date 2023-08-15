package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.VaccinationType;
import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVaccineTypeRepository extends JpaRepository<VaccineType, Integer> {

//    VaccineType findById(int id);
    @Query("SELECT vt FROM VaccineType vt WHERE vt.deleteFlag = false")
    List<VaccineType> findByDeleteFlagFalse();

}
