package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Account;

public interface IAccountService {

    void addNew(Account account);

    Integer findIdAccountByUserName(String username);
}
