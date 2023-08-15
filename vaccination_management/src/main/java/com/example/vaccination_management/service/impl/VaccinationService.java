package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IVaccinationDTO;
import com.example.vaccination_management.repository.IVaccinationRepository;
import com.example.vaccination_management.service.IVaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.vaccination_management.entity.Vaccination;
import com.example.vaccination_management.entity.VaccinationHistory;
import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.repository.IVaccinationHistoryRepository;
import com.example.vaccination_management.repository.IVaccineRepository;

import org.springframework.data.domain.PageRequest;


import java.util.List;


@Service
public class VaccinationService implements IVaccinationService {

    @Autowired
    private IVaccinationRepository iVaccinationRepository;

    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccinationHistoryRepository iVaccinationHistoryRepository;


    @Override
    public Page<IVaccinationDTO> getAllVaccination(String strSearch, Pageable pageable) {
        return iVaccinationRepository.getVaccinations(strSearch, pageable);
    }

    /**
     * LoanHTP
     * Adds a new vaccination history record.
     */
    @Override
    public void addVaccinationHistory(VaccinationHistory vaccinationHistory) {
        iVaccinationHistoryRepository.save(vaccinationHistory);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine.
     */
    @Override
    public List<Vaccination> findVaccinationByVaccine(Vaccine vaccine) {
        return iVaccinationRepository.findVaccinationByVaccineAndDeleteFlagFalse(vaccine);
    }

    /**
     * LoanHTP
     * Retrieves a vaccination record based on the provided ID.
     */
    @Override
    public Vaccination findVaccinationById(int id) {
        return iVaccinationRepository.findById(id);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations to display.
     */
    @Override
    public List<Vaccination> showVaccination() {
        return iVaccinationRepository.findAll();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccinations associated with the provided vaccine.
     */
    @Override
    public long getTotalVaccinationsByVaccine(Vaccine vaccine) {
        return iVaccinationRepository.countByVaccineAndDeleteFlagFalse(vaccine);
    }

    /**
     * LoanHTP
     * Retrieves a list of vaccinations based on the provided page number, size, and vaccine for pagination.
     */
    @Override
    public List<Vaccination> getVaccinationsByPageAndVaccine(int page, int size, Vaccine vaccine) {

        Pageable pageable = PageRequest.of(page,size);
        Page<Vaccination> vaccinationPage = iVaccinationRepository.findVaccinationByVaccineAndDeleteFlagFalse(vaccine,pageable);
        return vaccinationPage.getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of all vaccinations.
     */
    @Override
    public long getTotalVaccinations() {
        return iVaccinationRepository.countByDeleteFlagFalse();
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccinations based on the provided page number and size for pagination.
     */
    @Override
    public List<Vaccination> getVaccinationByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccination> vaccinationPage = iVaccinationRepository.findByDeleteFlagFalse(pageable);
        return vaccinationPage.getContent();
    }

}
