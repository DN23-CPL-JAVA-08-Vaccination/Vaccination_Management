package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRP;

    @Autowired
    private IPatientRepository iPatientRP;
    @Override
    public void insertAccount(String name, String password, String verificationCode, String email, Boolean enableFlag) {
        iAccountRP.insertAccount(name, password, verificationCode, email, enableFlag);
    }


    @Override
    public Integer findLatestAccountId() {
        return iAccountRP.findLatestAccountId();
    }

    @Override
    public List<Account> findAll() {
        return iAccountRP.findAll();
    }


    @Override
    public IAccountDetailDTO findAccountById(Integer id) {
        return iAccountRP.findAccountById(id);
    }


    @Override
    public void updateEnableFlagById(Boolean enableFlag, Integer id) {
        iAccountRP.updateEnableFlagById(enableFlag, id);
    }

    @Override
    public void deleteById(Integer integer) {
        iPatientRP.updateAccountIdByAccountId(integer);
        iAccountRP.deleteById(integer);
    }
}
