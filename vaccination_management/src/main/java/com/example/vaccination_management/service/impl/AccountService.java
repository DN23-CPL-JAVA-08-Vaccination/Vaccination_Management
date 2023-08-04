package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository accountRepository;

    /**
     * ThangLV
     * insert new Account of Patient
     */
    @Override
    public void addNew(Account account) {
        accountRepository.insertAccount(account.getEmail(), account.isEnableFlag(), account.getPassword(), account.getUserName());
    }

    @Override
    public void update(Account account) {
        Integer idAccount = accountRepository.findIdUserByUserName(account.getUserName());
        accountRepository.updateAccount(account.getEmail(), account.getUserName(), idAccount);
    }


    /**
     * ThangLV
     * find Id of Account by Username
     */
    @Override
    public Integer findIdAccountByUserName(String username) {
        return accountRepository.findIdUserByUserName(username);
    }

    /**
     * ThangLV
     * find Account by Username
     */
    @Override
    public Optional<Account> findAccountByUserName(String username) {
        return accountRepository.findAccountByUserName(username);
    }


}
