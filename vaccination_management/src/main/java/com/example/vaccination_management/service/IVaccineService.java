package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.dto.IVaccineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IVaccineService {

     /**
     * VuongLV
     * get all information of Vaccine, admin after login
     */
    List<Vaccine> findAll();

    /**
     * HuyLVN
     * get information of vaccines have delete flag is false, admin after login
     */
    public List<Vaccine> getVaccinesDeleteFlagFalse();

    /**
     * HuyLVN
     * get information of vaccines have delete flag is true, admin after login
     */
    public List<Vaccine> getVaccinesDeleteFlagTrue();

    /**
     * HuyLVN
     * get all information of vaccines of vaccine type, admin after login
     */
    public List<Vaccine> getVaccinesByVaccineType(VaccineType vaccineType);

    /**
     * HuyLVN
     * update information of vaccine, admin after login
     */
    public void updateVaccine(Vaccine updatedVaccine);

    /**
     * HuyLVN
     * remove vaccine from database, admin after login
     */
    public void destroyVaccine(int vaccineID) throws VaccineNotFoundException;

    /**
     * HuyLVN
     * temporarily delete vaccine and move them to recycle bin, admin after login
     */
    public void deleteVaccine(int vaccineID);

    /**
     * HuyLVN
     * get the information entered from the form and create a new vaccine, admin after login
     */
    public void saveVaccine(Vaccine vaccine);

    /**
     * HuyLVN
     * get all information of vaccine by ID, admin after login
     */
    public Vaccine getVaccineByID(int vaccineID) throws VaccineNotFoundException;

    /**
     * HuyLVN
     * restore vaccine from recycle bin, admin after login
     */
    public void restoreVaccine(int vaccineID);

    Page<IVaccineDTO> findAllVaccine(Pageable pageable);

    Page<IVaccineDTO> searchVaccine(Pageable pageable, String strSearch);

    Page<IVaccineDTO> getVaccineByType(Pageable pageable, Integer type);


    long count();

    <S extends Vaccine> S save(S entity);


    /**
     * LoanHTP
     * Retrieves a list of vaccines to display.
     */
    List<Vaccine> showVaccines();

    /**
     * LoanHTP
     * Retrieves a vaccine record based on the provided ID.
     */
    Vaccine findVaccineById(int id);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number and size for pagination.
     */
    List<Vaccine> getVaccinesByPage(int page, int size);

    /**
     * LoanHTP
     * Gets the total count of all vaccines.
     */
    long getTotalVaccines();

    /**
     * LoanHTP
     * Retrieves a list of vaccinations associated with the provided vaccine and pagination details.
     */
    long getTotalVaccinesByVaccineType(VaccineType vaccineType);

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided vaccine type.
     */
    List<Vaccine> getVaccinesByPageAndVaccineType(int page, int size, VaccineType vaccineType);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    List<Vaccine> getVaccinesByPageAndSearch(int page, int size, String searchQuery);

    /**
     * LoanHTP
     * Retrieves a list of vaccines based on the provided page number, size, and vaccine type for pagination.
     */
    long getTotalVaccinesBySearch(String searchQuery);

    /**
     * LoanHTP
     * Gets the total count of vaccines associated with the provided search query.
     */
    long getTotalVaccinesByVaccineTypeAndSearch(VaccineType vaccineType, String searchQuery);

    /**
     * LoanHTP
     * Retrieves a paginated list of vaccines based on the provided page number, size, vaccine type, and search query for pagination.
     */
    List<Vaccine> getVaccinesByPageAndVaccineTypeAndSearch(int page, int size, VaccineType vaccineType, String searchQuery);

}
