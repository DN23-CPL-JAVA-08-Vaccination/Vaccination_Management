package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccineType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVaccineTypeService {

    Page<VaccineType> findAllVaccine(String strSearch, Pageable pageable);

    <S extends VaccineType> Page<S> findAll(Example<S> example, Pageable pageable);

}
