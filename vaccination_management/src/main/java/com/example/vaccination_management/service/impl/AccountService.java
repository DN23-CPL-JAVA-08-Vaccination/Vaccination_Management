package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public void addNew(Account account) {
        accountRepository.insertAccount(account.getEmail(), account.isEnableFlag(), account.getPassword(), account.getUserName());
    }

    @Override
    public Integer findIdAccountByUserName(String username) {
        return accountRepository.findIdUserByUserName(username);
    }


}
