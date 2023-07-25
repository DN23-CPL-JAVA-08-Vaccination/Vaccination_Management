package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Position;

import java.util.List;

public interface IPositionService {

    /**
     * ThangLV
     * get list Position of Employee
     */
    List<Position> findAll();
}
