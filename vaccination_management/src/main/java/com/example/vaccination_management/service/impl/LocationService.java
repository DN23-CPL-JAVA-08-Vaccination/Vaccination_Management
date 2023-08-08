package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Location;
import com.example.vaccination_management.repository.ILocationRepository;
import com.example.vaccination_management.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private ILocationRepository iLocationRepository;
    @Override
    public List<Location> finAll() {
        return iLocationRepository.findAll();
    }
}
