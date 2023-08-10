package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Account;

import java.util.Optional;

public interface IAccountService {

    /**
     * ThangLV
     * insert new Account of Patient
     */
    void addNewOfEmployee(Account account);

    /**
     * ThangLV
     * insert new Account of Patient
     */
    void updateAccountOfEmployee(Account account);

    /**
     * ThangLV
     * find Id of Account by Username
     */
    Integer findIdAccountByUserName(String username);

    /**
     * ThangLV
     * find Account by Username
     */
    Optional<Account> findAccountByUserName(String username);



    /**
     * ThangLV
     * change password
     */
    void changePasswordLogin(String password, String username) ;
}
