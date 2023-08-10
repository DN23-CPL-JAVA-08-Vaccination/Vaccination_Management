package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.dto.IVaccineDTO;
import com.example.vaccination_management.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IVaccineService {
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

}
