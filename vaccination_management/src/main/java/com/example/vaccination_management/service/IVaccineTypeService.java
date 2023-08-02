package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;

import java.util.List;

public interface IVaccineTypeService {
    public List<VaccineType> getAllVaccineType();

    public VaccineType getVaccineTypeById(int vaccineTypeID) throws VaccineTypeNoFoundException;

    public void deleteVaccineType(int vaccineTypeID) throws VaccineTypeNoFoundException;

    public void saveVaccineType(VaccineType vaccineType);
}
