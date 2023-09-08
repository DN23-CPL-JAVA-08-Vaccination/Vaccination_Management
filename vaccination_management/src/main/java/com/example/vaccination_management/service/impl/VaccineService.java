package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccinationType;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccineService implements IVaccineService {

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Override
    public List<Vaccine> showVaccines() {
        return iVaccineRepository.findAll();
    }

    /**
     * LoanHTP
     * Retrieves a vaccine record based on the provided ID.
     */
    @Override
    public Vaccine findVaccineById(int id) {
        return iVaccineRepository.findById(id).orElse(null);
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number and size for pagination.
     */
    @Override
    public List<Vaccine> getVaccinesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccine> vaccinePage = iVaccineRepository.findByDeleteFlagFalse(pageable);
        return vaccinePage.getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of all vaccines.
     */
    @Override
    public long getTotalVaccines() {
        return iVaccineRepository.countByNameAndDeleteFlagFalseContainingIgnoreCase();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type.
     */
    @Override
    public long getTotalVaccinesByVaccineType(VaccineType vaccineType) {
        return iVaccineRepository.countByVaccineTypeAndDeleteFlagFalse(vaccineType);
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    public List<Vaccine> getVaccinesByPageAndVaccineType(int page, int size, VaccineType vaccineType) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccine> vaccinePage = iVaccineRepository.findByVaccineTypeAndDeleteFlagFalse(vaccineType, pageable);
        return vaccinePage.getContent();
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, and search query for pagination.
     */
    @Override
    public List<Vaccine> getVaccinesByPageAndSearch(int page, int size, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size);
        return iVaccineRepository.findByNameAndDeleteFlagFalseContainingIgnoreCase(searchQuery, pageable).getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided search query.
     */
    @Override
    public long getTotalVaccinesBySearch(String searchQuery) {
        return iVaccineRepository.countByNameAndDeleteFlagFalseContainingIgnoreCase(searchQuery);
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, vaccine type, and search query for pagination.
     */

    public List<Vaccine> getVaccinesByPageAndVaccineTypeAndSearch(int page, int size, VaccineType vaccineType, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size);
        return iVaccineRepository.findByVaccineTypeAndNameAndDeleteFlagFalseContainingIgnoreCase(vaccineType, searchQuery, pageable).getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type and search query.
     */
    @Override
    public long getTotalVaccinesByVaccineTypeAndSearch(VaccineType vaccineType, String searchQuery) {
        return iVaccineRepository.countByVaccineTypeAndNameAndDeleteFlagFalseContainingIgnoreCase(vaccineType, searchQuery);
    }

}
