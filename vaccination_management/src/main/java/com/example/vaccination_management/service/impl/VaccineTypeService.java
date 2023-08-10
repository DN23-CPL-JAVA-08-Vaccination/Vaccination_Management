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
     * get all information of vaccine types, admin after login
     */
    @Override
    public List<VaccineType> getAllVaccineType() {
        return iVaccineTypeRepository.findAll();
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
        iVaccineTypeRepository.save(vaccineType);
    }

    /**
     * HuyLVN
     * remove vaccine type from database, admin after login
     */
    @Override
    public void deleteVaccineType(int vaccineTypeID) throws VaccineTypeNoFoundException {
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
        return iVaccineTypeRepository.getAllVaccineType(strSearch,pageable);
    }
    @Override
    public <S extends VaccineType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return iVaccineTypeRepository.findAll(example, pageable);

    }
}
