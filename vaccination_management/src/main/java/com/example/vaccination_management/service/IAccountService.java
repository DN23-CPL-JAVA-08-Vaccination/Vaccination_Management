package com.example.vaccination_management.service;

import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.dto.IAccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
    /**
     * Quangvt
     * count all account active
     */
    long countAllAccount();
}
