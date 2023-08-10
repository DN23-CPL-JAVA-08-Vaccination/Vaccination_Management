package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVaccineTypeService {
    /**
     * HuyLVN
     * get all information of vaccine types, admin after login
     */
    public List<VaccineType> getAllVaccineType();

    /**
     * HuyLVN
     * get information of vaccine type by ID, admin after login
     */
    public VaccineType getVaccineTypeById(int vaccineTypeID) throws VaccineTypeNoFoundException;

    /**
     * HuyLVN
     * remove vaccine type from database, admin after login
     */
    public void deleteVaccineType(int vaccineTypeID) throws VaccineTypeNoFoundException;

    /**
     * HuyLVN
     * get the information entered from the form and create a new vaccine type, admin after login
     */
    public void saveVaccineType(VaccineType vaccineType);

    Page<VaccineType> findAllVaccine(String strSearch, Pageable pageable);

    <S extends VaccineType> Page<S> findAll(Example<S> example, Pageable pageable);

}
