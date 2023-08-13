package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface IVaccinationRepository extends JpaRepository<Vaccination, Integer> {
    List<Vaccination> findByDeleteFlagTrue();
    Vaccination findById(int id);
    Page<Vaccination> findByDeleteFlagFalse(Pageable pageable);
//    Page<Vaccination> findByDeleteFlagTrue(Pageable pageable);
}
