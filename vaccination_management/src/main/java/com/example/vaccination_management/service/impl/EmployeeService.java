package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Employee;
import com.example.vaccination_management.repository.IAccountRoleRepository;
import com.example.vaccination_management.repository.IEmployeeRepository;
import com.example.vaccination_management.service.IAccountRoleService;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountRoleService accountRoleService;

    @Override
    public List<Employee> findAll() {
        return null;
    }

    /**
     * ThangLV
     * insert Employee, Account of Employee, AccountRole of Account
     */
    @Override
    public void create(EmployeeCreateDTO employeeDTO) {
        Account account = new Account();
        account.setUserName(employeeDTO.getIdCard());
        account.setEmail(employeeDTO.getEmail());
        account.setEnableFlag(true);
        account.setPassword("123");
        accountService.addNew(account);
        Integer idAccount = accountService.findIdAccountByUserName(account.getUserName());
        accountRoleService.insertAccountRole(idAccount , 3);
        employeeRepository.createNewEmployee(employeeDTO.getAddress(),employeeDTO.getBirthday(),true,employeeDTO.isGender(),employeeDTO.getIdCard(),employeeDTO.getName(),employeeDTO.getPhone(),idAccount,employeeDTO.getPosition().getId());
    }

    @Override
    public void delete(Employee employee) {

    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    public EmployeeListDTO getInforById(int i) {
        return employeeRepository.getInforById(i);
    }


    /**
     * ThangLV
     * get list employee, Page used Pagination
     */
    @Override
    public Page<EmployeeListDTO> searchByName(String name, Pageable pageable) {
        return employeeRepository.findEmployeeList(name, pageable);
    }

    /**
     * ThangLV
     * check duplicate Phone of Employee table
     */
    @Override
    public Integer findByPhone(String phone) {
        return employeeRepository.findByPhone(phone);
    }

    /**
     * ThangLV
     * check duplicate IdCard of Employee table
     */
    @Override
    public Integer findByIdCard(String idCard) {
        return employeeRepository.findByIdCard(idCard);
    }

    /**
     * ThangLV
     * check duplicate Email of Employee table
     */
    @Override
    public Integer findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
