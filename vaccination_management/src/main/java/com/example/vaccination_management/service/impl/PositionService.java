package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Position;
import com.example.vaccination_management.repository.IPositionRepository;
import com.example.vaccination_management.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService implements IPositionService {

    @Autowired
    private IPositionRepository positionRepository;


    /**
     * ThangLV
     * get list Position of Employee
     */
    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }
}
