package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Vaccine;
import com.example.vaccination_management.entity.VaccineType;
import com.example.vaccination_management.exception.VaccineNotFoundException;
import com.example.vaccination_management.exception.VaccineTypeNoFoundException;
import com.example.vaccination_management.repository.IVaccineTypeRepository;
import com.example.vaccination_management.service.IVaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineTypeService implements IVaccineTypeService {
    @Autowired
    private IVaccineTypeRepository iVaccineTypeRepository;

    @Override
    public List<VaccineType> getAllVaccineType() {
        return iVaccineTypeRepository.findAll();
    }

    @Override
    public VaccineType getVaccineTypeById(int vaccineTypeID) throws VaccineTypeNoFoundException {
        Optional<VaccineType> vaccineType = iVaccineTypeRepository.findById(vaccineTypeID);

        if (vaccineType.isPresent()) {
            return vaccineType.get();
        }

        throw new VaccineTypeNoFoundException("Couldn't find any vaccine types with ID");
    }

    @Override
    public void saveVaccineType(VaccineType vaccineType) {
        iVaccineTypeRepository.save(vaccineType);
    }

    @Override
    public void deleteVaccineType(int vaccineTypeID) throws VaccineTypeNoFoundException {
        Long count = iVaccineTypeRepository.countById(vaccineTypeID);

        if (count == null || count == 0) {
            throw new VaccineTypeNoFoundException("Couldn't find any vaccine types with ID");
        }
        iVaccineTypeRepository.deleteById(vaccineTypeID);
    }
}
