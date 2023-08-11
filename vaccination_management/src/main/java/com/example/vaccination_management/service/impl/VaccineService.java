package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.repository.IVaccineRepository;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.example.vaccination_management.dto.IVaccineDTO;
import org.springframework.data.domain.PageRequest;


import java.util.List;


@Service
public class VaccineService implements IVaccineService {


    @Autowired
    private IVaccineRepository iVaccineRepository;

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    /**
     * HuyLVN
     * get information of vaccines have delete flag is false, admin after login
     */
    @Override
    public List<Vaccine> getVaccinesDeleteFlagFalse() {
        return iVaccineRepository.findByDeleteFlagFalse();
    }

    /**
     * HuyLVN
     * get information of vaccines have delete flag is true, admin after login
     */
    @Override
    public List<Vaccine> getVaccinesDeleteFlagTrue() {
        return iVaccineRepository.findByDeleteFlagTrue();
    }

    /**
     * HuyLVN
     * get all information of vaccines of vaccine type, admin after login
     */
    @Override
    public List<Vaccine> getVaccinesByVaccineType(VaccineType vaccineType) {
        return iVaccineRepository.getVaccineByVaccineType(vaccineType);
    }

    /**
     * HuyLVN
     * get the information entered from the form and create a new vaccine, admin after login
     */
    @Override
    public void saveVaccine(Vaccine vaccine) {
        LocalDateTime createDate = LocalDateTime.now();
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        vaccine.setCreateDate(createDate.format(formatter));
        vaccine.setUpdateDate(updateDate.format(formatter));
        vaccine.setDeleteFlag(false);

        iVaccineRepository.save(vaccine);
    }

    /**
     * HuyLVN
     * get all information of vaccine by ID, admin after login
     */
    @Override
    public Vaccine getVaccineByID(int vaccineID) throws VaccineNotFoundException {
        Optional<Vaccine> vaccine = iVaccineRepository.findById(vaccineID);

        if (vaccine.isPresent()) {
            return vaccine.get();
        }

        throw new VaccineNotFoundException("Couldn't find any vaccines with ID");
    }

    /**
     * HuyLVN
     * update information of vaccine, admin after login
     */
    @Override
    public void updateVaccine(Vaccine updatedVaccine) {
        LocalDateTime updateDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        updatedVaccine.setUpdateDate(updateDate.format(formatter));

        iVaccineRepository.save(updatedVaccine);
    }

    /**
     * HuyLVN
     * remove vaccine from database, admin after login
     */
    @Override
    public void destroyVaccine(int vaccineID) throws VaccineNotFoundException {
        Long count = iVaccineRepository.countById(vaccineID);

        if (count == null || count == 0) {
            throw new VaccineNotFoundException("Couldn't find any vaccines with ID");
        }
        iVaccineRepository.deleteById(vaccineID);
    }

    /**
     * HuyLVN
     * temporarily delete vaccine and move them to recycle bin, admin after login
     */
    @Override
    public void deleteVaccine(int vaccineID) {
        Optional<Vaccine> vaccineOptional = iVaccineRepository.findById(vaccineID);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();

            vaccine.setDeleteFlag(true);

            iVaccineRepository.save(vaccine);
        }
    }

    /**
     * HuyLVN
     * restore vaccine from recycle bin, admin after login
     */
    @Override
    public void restoreVaccine(int vaccineID) {
        Optional<Vaccine> vaccineOptional = iVaccineRepository.findById(vaccineID);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();

            vaccine.setDeleteFlag(false);

            iVaccineRepository.save(vaccine);
        }
    }

    /**
     * QuangVT
     * get all vaccine
     */
    @Override
    public Page<IVaccineDTO> findAllVaccine(Pageable pageable) {
        return iVaccineRepository.getAllVaccine(pageable);
    }

    /**
     * QuangVT
     * search  vaccine
     */
    @Override
    public Page<IVaccineDTO> searchVaccine(Pageable pageable, String strSearch) {
        return iVaccineRepository.searchVaccine(strSearch, pageable);
    }

    /**
     * QuangVT
     * get vaccine by type
     */
    @Override
    public Page<IVaccineDTO> getVaccineByType(Pageable pageable, Integer type) {
        return iVaccineRepository.getVaccineByType(pageable, type);
    }


    @Override
    public long count() {
        return iVaccineRepository.count();
    }

    @Override
    public <S extends Vaccine> S save(S entity) {
        return iVaccineRepository.save(entity);
    }

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
        Page<Vaccine> vaccinePage = iVaccineRepository.findAll(pageable);
        return vaccinePage.getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of all vaccines.
     */
    @Override
    public long getTotalVaccines() {
        return iVaccineRepository.count();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type.
     */
    @Override
    public long getTotalVaccinesByVaccineType(VaccineType vaccineType) {
        return iVaccineRepository.countByVaccineType(vaccineType);
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    @Override
    public List<Vaccine> getVaccinesByPageAndVaccineType(int page, int size, VaccineType vaccineType) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Vaccine> vaccinePage = iVaccineRepository.findByVaccineType(vaccineType, pageable);
        return vaccinePage.getContent();
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, and search query for pagination.
     */
    @Override
    public List<Vaccine> getVaccinesByPageAndSearch(int page, int size, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size);
        return iVaccineRepository.findByNameContainingIgnoreCase(searchQuery, pageable).getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided search query.
     */
    @Override
    public long getTotalVaccinesBySearch(String searchQuery) {
        return iVaccineRepository.countByNameContainingIgnoreCase(searchQuery);
    }

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, vaccine type, and search query for pagination.
     */
    @Override
    public List<Vaccine> getVaccinesByPageAndVaccineTypeAndSearch(int page, int size, VaccineType vaccineType, String searchQuery) {
        Pageable pageable = PageRequest.of(page, size);
        return iVaccineRepository.findByVaccineTypeAndNameContainingIgnoreCase(vaccineType, searchQuery, pageable).getContent();
    }

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type and search query.
     */
    @Override
    public long getTotalVaccinesByVaccineTypeAndSearch(VaccineType vaccineType, String searchQuery) {
        return iVaccineRepository.countByVaccineTypeAndNameContainingIgnoreCase(vaccineType, searchQuery);
    }


}
