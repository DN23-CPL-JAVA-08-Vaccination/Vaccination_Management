package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Account;

public interface IAccountService {

    /**
     * ThangLV
     * insert new Account of Patient
     */
    void addNew(Account account);

    /**
     * ThangLV
     * find Id of Account by Username
     */
    Integer findIdAccountByUserName(String username);
}
