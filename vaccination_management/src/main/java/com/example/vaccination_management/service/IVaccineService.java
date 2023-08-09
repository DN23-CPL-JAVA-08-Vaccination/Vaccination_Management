package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVaccineService {

    Page<IVaccineDTO> findAllVaccine(Pageable pageable);

    Page<IVaccineDTO> searchVaccine(Pageable pageable, String strSearch);

    Page<IVaccineDTO> getVaccineByType(Pageable pageable, Integer type);


    long count();

    <S extends Vaccine> S save(S entity);
}
