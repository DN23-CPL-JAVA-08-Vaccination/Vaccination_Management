package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.repository.IAccountRoleRepository;
import com.example.vaccination_management.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleService implements IAccountRoleService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IAccountRoleRepository accountRoleRepository;

    /**
     * ThangLV
     * insert new Account Role
     */
    @Override
    public void insertAccountRole(Integer accountId, Integer role) {
        accountRoleRepository.insertAccountRole(accountId, role);
    }

    /**
     * ThangLV
     * check password in Account
     * Bỏ hàm này ở đây để tránh dependency injection vòng
     */
    @Override
    public Boolean checkPassword(String password, String username) {
        String currentPassword = accountRepository.getCurrentPassword(username);
        return passwordEncoder.matches(password, currentPassword);
    }

}
