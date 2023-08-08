package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.beans.Transient;

@Repository
public interface IVaccineRepository extends JpaRepository<Vaccine, Integer> {
//    @Transactional
//    @Modifying
//    @Query(value = "SELECT vaccine.name FROM vaccine")
//    List<I>
}
