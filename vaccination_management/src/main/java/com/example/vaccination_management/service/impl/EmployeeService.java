package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.EmployeeCreateDTO;
import com.example.vaccination_management.dto.EmployeeListDTO;
import com.example.vaccination_management.dto.InfoEmployeeAccountDTO;
import com.example.vaccination_management.dto.InforEmployeeDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Employee;
import com.example.vaccination_management.entity.Position;
import com.example.vaccination_management.repository.IEmployeeRepository;
import com.example.vaccination_management.service.IAccountRoleService;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        String randomPassword = generateRandom(6);

        Account account = new Account();
        account.setUserName(employeeDTO.getIdCard());
        account.setEmail(employeeDTO.getEmail());
        account.setEnableFlag(true);
//        account.setPassword(passwordEncoder.encode(randomPassword));
        account.setPassword(passwordEncoder.encode("123"));
        accountService.addNew(account);
        Integer idAccount = accountService.findIdAccountByUserName(account.getUserName());
        accountRoleService.insertAccountRole(idAccount, 2);
        employeeRepository.createNewEmployee(employeeDTO.getAddress(), employeeDTO.getBirthday(), false, employeeDTO.isGender()
                , employeeDTO.getIdCard(), employeeDTO.getName(), employeeDTO.getPhone(), employeeDTO.getImage(), idAccount, employeeDTO.getPosition().getId());
    }

    @Override
    public void delete(int index) {
        employeeRepository.deleteEmployee(index);
    }

    @Override
    public void update(EmployeeCreateDTO employeeDTO) {
        Account account = new Account();
        account.setUserName(employeeDTO.getIdCard());
        account.setEmail(employeeDTO.getEmail());
        accountService.update(account);
        employeeRepository.updateEmployee(employeeDTO.getAddress(), employeeDTO.getBirthday(), employeeDTO.isGender(), employeeDTO.getIdCard()
                , employeeDTO.getImage(), employeeDTO.getName(), employeeDTO.getPhone(), employeeDTO.getPosition().getId(), employeeDTO.getId());
    }

    /**
     * ThangLV
     * get information of Employee use Update
     */
    @Override
    public EmployeeCreateDTO getInforUpdateById(int id) {

        InforEmployeeDTO employeeDTO = getInforById(id);
        System.out.println(employeeDTO.getPositionId());
        System.out.println(employeeDTO.getPositionName());
        EmployeeCreateDTO employeeUpdateDTO = new EmployeeCreateDTO();

        employeeUpdateDTO.setId(employeeDTO.getId());
        employeeUpdateDTO.setName(employeeDTO.getName());
        employeeUpdateDTO.setAddress(employeeDTO.getAddress());
        employeeUpdateDTO.setBirthday(employeeDTO.getBirthday());
        employeeUpdateDTO.setGender(employeeDTO.getGender());
        employeeUpdateDTO.setIdCard(employeeDTO.getIdCard());
        employeeUpdateDTO.setPhone(employeeDTO.getPhone());
        employeeUpdateDTO.setImage(employeeDTO.getImage());


        int positionId = Integer.parseInt(employeeDTO.getPositionId());
        String positionName = employeeDTO.getPositionName();

        employeeUpdateDTO.setPosition(new Position(positionId, positionName));
        employeeUpdateDTO.setEmail(employeeDTO.getEmail());
        return employeeUpdateDTO;
    }

    /**
     * ThangLV
     * get information of Employee by id
     */
    @Override
    public InforEmployeeDTO getInforById(int i) {
        return employeeRepository.getInforById(i);
    }

    /**
     * ThangLV
     * get information of Employee by username
     */
    @Override
    public InfoEmployeeAccountDTO getInforByUsername(String username) {
        return employeeRepository.getInforByUsername(username);
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

    /**
     * ThangLV
     * random Password
     */
    public static String generateRandom(int length) {
        String allAlpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String randomId = "";

        for (int i = 0; i < length; i++) {
            int randomIndex = (int) Math.floor(52 * Math.random());
            randomId += allAlpha.charAt(randomIndex);
        }
        return randomId;
    }

    @Override
    public List<EmployeeListDTO> getEmployeeByPage(String nameSearch,Pageable pageable) {
        Page<EmployeeListDTO> employeeListDTOPage = employeeRepository.findEmployeeList(nameSearch, pageable);
        return employeeListDTOPage.getContent();
    }

    @Override
    public long getTotalEmployee(String name) {
        return employeeRepository.getTotalEmployee(name);
    }
}
