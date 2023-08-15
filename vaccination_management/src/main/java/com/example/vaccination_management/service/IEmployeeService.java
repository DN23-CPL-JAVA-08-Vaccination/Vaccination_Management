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

    /**
     * ThangLV
     * insert Employee, Account of Employee, AccountRole of Account
     */
    void create(EmployeeCreateDTO employeeDTO);

    /**
     * ThangLV
     * delete Employee By id
     */
    void delete(int index);

    /**
     * ThangLV
     * update Employee, Account of Employee
     */
    void update(EmployeeCreateDTO employeeDTO);

    /**
     * ThangLV
     * get information of Employee use Update
     */
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

    /**
     * ThangLV
     * get list employee, Page used Pagination
     */
    List<EmployeeListDTO> getEmployeeByPage(String nameSearch, Pageable pageable);

    /**
     * ThangLV
     * get total Employee by Name
     */
    long getTotalEmployee(String nameSearch);
    /**
     * Quangvt
     * get Employee by id
     */
    Employee getEmployeeById(Integer id);
    /**
     * Quangvt
     * count all Employee
     */
    long countAllEmployee();
}
