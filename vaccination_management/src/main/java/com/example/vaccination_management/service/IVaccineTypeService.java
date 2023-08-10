package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IVaccineTypeService {

    /**
     * LoanHTP
     * Retrieves a list of vaccine types to display.
     */
    List<VaccineType> showVaccineType();

    /**
     * LoanHTP
     * Retrieves a list of vaccine types to display.
     */
    VaccineType findVaccineTypeById(int id);
}
