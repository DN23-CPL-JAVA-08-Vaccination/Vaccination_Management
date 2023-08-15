package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineTypeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;


@Service
public class VaccineTypeService implements IVaccineTypeService {

    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    /**
     * HuyLVN
     * get information of vaccines have delete flag is false, admin after login
     */
    @Override
    public List<VaccineType> getVaccinesTypeDeleteFlagFalse() {
        return iVaccineTypeRepository.findByDeleteFlagFalse();
    }

    /**
     * HuyLVN
     * get information of vaccines have delete flag is false, admin after login
     */
    @Override
    public List<VaccineType> getVaccinesTypeDeleteFlagTrue() {
        return iVaccineTypeRepository.findByDeleteFlagTrue();
    }

    /**
     * LoanHTP
     * Retrieves a list of all available vaccine types.
     */
    @Override
    public List<VaccineType> showVaccineType() {
        return iVaccineTypeRepository.findByDeleteFlagsFalse();
    }

    /**
     * HuyLVN
     * get information of vaccine type by ID, admin after login
     */
    @Override
    public VaccineType getVaccineTypeById(int vaccineTypeID) throws VaccineTypeNoFoundException {
        Optional<VaccineType> vaccineType = iVaccineTypeRepository.findById(vaccineTypeID);

        if (vaccineType.isPresent()) {
            return vaccineType.get();
        }

        throw new VaccineTypeNoFoundException("Couldn't find any vaccine types with ID");
    }

    /**
     * HuyLVN
     * get the information entered from the form and create a new vaccine type, admin after login
     */
    @Override
    public void saveVaccineType(VaccineType vaccineType) {
        vaccineType.setDeleteFlag(false);

        iVaccineTypeRepository.save(vaccineType);
    }

    /**
     * HuyLVN
     * remove vaccine type from database, admin after login
     */
    @Override
    public void destroyVaccineType(int vaccineTypeID) throws VaccineTypeNoFoundException {
        Long count = iVaccineTypeRepository.countById(vaccineTypeID);

        if (count == null || count == 0) {
            throw new VaccineTypeNoFoundException("Couldn't find any vaccine types with ID");
        }
        iVaccineTypeRepository.deleteById(vaccineTypeID);
    }

    /**
     * QuangVT
     * get all vaccine type
     */
    @Override
    public Page<VaccineType> findAllVaccine(String strSearch, Pageable pageable) {
        return iVaccineTypeRepository.getAllVaccineType(strSearch, pageable);
    }

    @Override
    public <S extends VaccineType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return iVaccineTypeRepository.findAll(example, pageable);
    }

    /**
     * LoanHTP
     * Retrieves a vaccine type record based on the provided ID.
     */
    @Override
    public VaccineType findVaccineTypeById(int id) {
        return iVaccineTypeRepository.findById(id).orElse(null);

    }
     /**
     * HuyLVN
     * temporarily delete vaccine type and move them to recycle bin, admin after login
     */
    @Override
    public void deleteVaccineType(int vaccineTypeID) {
        Optional<VaccineType> vaccineTypeOptional = iVaccineTypeRepository.findById(vaccineTypeID);

        if (vaccineTypeOptional.isPresent()) {
            VaccineType vaccineType = vaccineTypeOptional.get();

            vaccineType.setDeleteFlag(true);

            iVaccineTypeRepository.save(vaccineType);
        }
    }

    /**
     * HuyLVN
     * restore vaccine from recycle bin, admin after login
     */
    @Override
    public void restoreVaccineType(int vaccineTypeID) {
        Optional<VaccineType> vaccineTypeOptional = iVaccineTypeRepository.findById(vaccineTypeID);

        if (vaccineTypeOptional.isPresent()) {
            VaccineType vaccineType = vaccineTypeOptional.get();

            vaccineType.setDeleteFlag(false);

            iVaccineTypeRepository.save(vaccineType);
        }
    }
}
