package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.dto.InfoEmployeeAccountDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {

    List<Employee> findAll();

    void create(EmployeeCreateDTO employeeDTO);

    void delete(int index);

    void update(EmployeeCreateDTO employeeDTO);

    EmployeeCreateDTO getInforUpdateById(int id);

    /**
     * ThangLV
     * get information of Employee by id
     */
    InforEmployeeDTO getInforById(int i);

    /**
     * ThangLV
     * get information of Employee by id
     */
    InfoEmployeeAccountDTO getInforByUsername(String username);

    /**
     * ThangLV
     * get list employee, Page used Pagination
     */
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

    List<EmployeeListDTO> getEmployeeByPage(String nameSearch, Pageable pageable);

    long getTotalEmployee(String nameSearch);
}
