package com.example.vaccination_management.service;


import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Account;

import java.util.List;

public interface IAccountService {


    void insertAccount(String name, String password, String verificationCode, String email, Boolean enableFlag);



    Integer findLatestAccountId();

    List<Account> findAll();

    IAccountDetailDTO findAccountById(Integer id);
}
