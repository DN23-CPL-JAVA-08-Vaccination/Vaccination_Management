package com.example.vaccination_management.service;


import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.IAccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAccountService {


    void insertAccount(String name, String password, String email, Boolean enableFlag);



    Integer findLatestAccountId();

    List<Account> findAll();

    IAccountDetailDTO findAccountById(Integer id);

    void updateEnableFlagById(Boolean enableFlag, Integer id);

    void deleteById(Integer integer);


    IAccountDTO findAllByUsername(String userName);

    Integer finByUserName(String userName);

    void updateVerificationCodeByUserName(String verificationCode, String userName);

    Integer checkVerificationCode(String verificationCode);

    void rePasswordByVerificationCode(String password, String verificationCode);



    List<Account> getAccountByPage(String userName, Pageable pageable);

    long getTotalAccount(String userName);
}
