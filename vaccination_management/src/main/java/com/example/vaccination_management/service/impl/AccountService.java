package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.IAccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.repository.IPatientRepository;
import com.example.vaccination_management.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IAccountRepository iAccountRP;

    @Autowired
    private IPatientRepository iPatientRP;

    /**
     * ThangLV
     * insert new Account of Patient
     */
    @Override
    public void addNewOfEmployee(Account account) {
        accountRepository.insertAccount(account.getEmail(), account.getEnableFlag(), account.getPassword(), account.getUserName());
    }

    /**
     * ThangLV
     * update Account when update Employee of Patient
     */
    @Override
    public void updateAccountOfEmployee(Account account) {
        Integer idAccount = accountRepository.findIdUserByUserName(account.getUserName());
        accountRepository.updateAccount(account.getEmail(), account.getUserName(), idAccount);

    }

    /**
       * TLINH
       * insert account information
     */
    @Override
    public void insertAccount(String name, String password, String email, Boolean enableFlag) {
        iAccountRP.insertAccount(name, password, email, enableFlag);
    }

    /**
       * TLINH
       * find account id max
     */
    @Override
    public Integer findLatestAccountId() {
        return iAccountRP.findLatestAccountId();
    }

    
    /**
       * TLINH
       * view list account
     */
    @Override
    public List<Account> findAll() {
        return iAccountRP.findAll();
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

    /**
     * ThangLV
     * change password Account by Username
     */
    @Override
    public void changePasswordLogin(String password, String username) {
        accountRepository.changePassword(password,username);
    }

    /**
     * TLINH
     * find account by id
     */
    @Override
    public IAccountDetailDTO findAccountById(Integer id) {
        return iAccountRP.findAccountById(id);
    }


    
    /**
       * TLINH
       * update enable flag by id 
     */
    @Override
    public void updateEnableFlagById(Boolean enableFlag, Integer id) {
        iAccountRP.updateEnableFlagById(enableFlag, id);
    }

    
    /**
       * TLINH
       * delete patient by id and update account id = null
     */
    @Override
    public void deleteById(Integer integer) {
        iPatientRP.updateAccountIdByAccountId(integer);
        iAccountRP.deleteById(integer);
    }


    /**
       * TLINH
       * find all account by userName
     */
    @Override
    public IAccountDTO findAllByUsername(String userName) {
        return iAccountRP.findAllByUsername(userName);
    }

    
    /**
       * TLINH
       * count is the number userName in table account
     */
    @Override
    public Integer finByUserName(String userName) {
        return iAccountRP.finByUserName(userName);
    }


    /**
       * TLINH
       * update verification code by user name
     */
    @Override
    public void updateVerificationCodeByUserName(String verificationCode, String userName) {
        iAccountRP.updateVerificationCodeByUserName(verificationCode, userName);
    }


    /**
       * TLINH
       * count is the number verification code in table account 
     */
    @Override
    public Integer checkVerificationCode(String verificationCode) {
        return iAccountRP.checkVerificationCode(verificationCode);
    }

    
    /**
       * TLINH
       * update password by verification code
     */
    @Override
    public void rePasswordByVerificationCode(String password, String verificationCode) {
        iAccountRP.rePasswordByVerificationCode(password, verificationCode);
    }


    /**
       * TLINH
       * paging by username
     */
    @Override
    public List<Account> getAccountByPage(String userName, Pageable pageable){
        Page<Account> accountPage=iAccountRP.findAccountListPage(userName, pageable);
        return accountPage.getContent();
    }

    /**
       * TLINH
       * count is the number username
     */
    @Override
    public long getTotalAccount(String userName){
        return iAccountRP.getTotalAccount(userName);
    }

    /**
     * VTquang
     * count all account active
     */

    @Override
    public long countAllAccount(){
        return iAccountRP.countTotalAccount();
    }



}
