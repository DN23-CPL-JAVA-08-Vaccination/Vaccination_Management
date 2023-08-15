package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.*;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.entity.Employee;
import com.example.vaccination_management.entity.Position;
import com.example.vaccination_management.repository.IEmployeeRepository;
import com.example.vaccination_management.service.IAccountRoleService;
import com.example.vaccination_management.service.IAccountService;
import com.example.vaccination_management.service.IEmailService;
import com.example.vaccination_management.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

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

        String password = generatePassword(8);

        Account account = new Account();
        account.setUserName(employeeDTO.getIdCard());
        account.setEmail(employeeDTO.getEmail());
        account.setEnableFlag(true);
        account.setPassword(passwordEncoder.encode(password));
        accountService.addNewOfEmployee(account);

        Integer idAccount = accountService.findIdAccountByUserName(account.getUserName());
        if (employeeDTO.getPosition().getId() == 1) {
            accountRoleService.insertAccountRole(idAccount, 2);
        } else if (employeeDTO.getPosition().getId() == 2) {
            accountRoleService.insertAccountRole(idAccount, 1);
        }

        employeeRepository.createNewEmployee(employeeDTO.getAddress(), employeeDTO.getBirthday(), false, employeeDTO.isGender()
                , employeeDTO.getIdCard(), employeeDTO.getName(), employeeDTO.getPhone(), employeeDTO.getImage(), idAccount, employeeDTO.getPosition().getId());

        MailEmployeeDTO mailEmployeeDTO = new MailEmployeeDTO(employeeDTO.getName(), employeeDTO.getBirthday(), employeeDTO.isGender(), employeeDTO.getIdCard(), employeeDTO.getPhone(), employeeDTO.getEmail(), employeeDTO.getAddress(), employeeDTO.getIdCard(), password);

        sendEmail(mailEmployeeDTO);
    }

    /**
     * ThangLV
     * delete Employee By id
     */
    @Override
    public void delete(int index) {
        employeeRepository.deleteEmployee(index);
    }


    /**
     * ThangLV
     * update Employee, Account of Employee
     */
    @Override
    public void update(EmployeeCreateDTO employeeDTO) {
        Account account = new Account();
        account.setUserName(employeeDTO.getIdCard());
        account.setEmail(employeeDTO.getEmail());
        accountService.updateAccountOfEmployee(account);
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
    private static String generatePassword(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(SECURE_RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    /**
     * ThangLV
     * get list employee, Page used Pagination
     */
    @Override
    public List<EmployeeListDTO> getEmployeeByPage(String nameSearch, Pageable pageable) {
        Page<EmployeeListDTO> employeeListDTOPage = employeeRepository.findEmployeeList(nameSearch, pageable);
        return employeeListDTOPage.getContent();
    }

    /**
     * ThangLV
     * get total Employee by Name
     */
    @Override
    public long getTotalEmployee(String name) {
        return employeeRepository.getTotalEmployee(name);
    }

    @Override
    public Employee getEmployeeById(Integer id){
        return   employeeRepository.getById(id);
    }

    /**
     * ThangLV
     * send mail Account to Employee
     */
    public void sendEmail(MailEmployeeDTO mailEmployeeDTO) {
        try {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setTo(mailEmployeeDTO.getEmail());
            emailDTO.setSubject("THÔNG BÁO CẤP TÀI KHOẢN TIÊM CHỦNG ĐÀ NẴNG");
            Map<String, Object> props = new HashMap<>();
            props.put("name", mailEmployeeDTO.getName());
            props.put("password", mailEmployeeDTO.getPassword());
            props.put("username", mailEmployeeDTO.getIdCard());
            props.put("phone", mailEmployeeDTO.getPhone());
            props.put("gender", mailEmployeeDTO.isGender());
            props.put("address", mailEmployeeDTO.getAddress());
            props.put("birthday", mailEmployeeDTO.getBirthday());
            props.put("idCard", mailEmployeeDTO.getIdCard());
            props.put("email", mailEmployeeDTO.getBirthday());
            emailDTO.setProps(props);
            sendHtmlMail(emailDTO, "Admin/Email/form_email_employee");
        } catch (MessagingException exp) {
            exp.printStackTrace();
        }
    }

    /**
     * TLINH
     * set html interface for email
     */
    public void sendHtmlMail(EmailDTO emailDTO, String templateName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        Context context = new Context();
        context.setVariables(emailDTO.getProps());
        String html = templateEngine.process(templateName, context);
        helper.setTo(emailDTO.getTo());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(html, true);
        mailSender.send(message);

    }

}
