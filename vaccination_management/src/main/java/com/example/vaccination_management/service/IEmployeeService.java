package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {

    List<Employee> findAll();
    void create(EmployeeCreateDTO employeeDTO);
    void delete(Employee employee);
    void update(Employee employee);
    EmployeeListDTO getInforById(int i);
    Page<EmployeeListDTO> searchByName(String name, Pageable pageable);

    /**
     * ThangLV
     * check duplicate Phone of Employee table
     */
    Integer findByPhone(String phoneNumber);

    /**
     * ThangLV
     * check duplicate IdCard of Employee table
     */
    Integer findByIdCard(String idCard);

    /**
     * ThangLV
     * check duplicate Email of Employee table
     */
    Integer findByEmail(String email);
}
