package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.repository.ILocationRepository;
import com.example.vaccination_management.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private ILocationRepository iLocationRepository;

    /**
     * TLINH
     * find all location
     */
    @Override
    public List<Location> findAll() {
        return iLocationRepository.findAll();
    }
}

