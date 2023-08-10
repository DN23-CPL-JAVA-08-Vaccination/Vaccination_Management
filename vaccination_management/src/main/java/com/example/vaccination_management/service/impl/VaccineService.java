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
        return iVaccineRepository.searchVaccine(strSearch ,pageable);
    }

    /**
     * QuangVT
     * get vaccine by type
     */
    @Override
    public Page<IVaccineDTO> getVaccineByType(Pageable pageable, Integer type) {
        return iVaccineRepository.getVaccineByType(pageable,type);
    }


    @Override
    public long count() {
        return iVaccineRepository.count();
    }

    @Override
    public <S extends Vaccine> S save(S entity) {
        return iVaccineRepository.save(entity);
    }
}
