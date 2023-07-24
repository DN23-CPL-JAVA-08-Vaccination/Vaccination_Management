package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.repository.IAccountRoleRepository;
import com.example.vaccination_management.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountRoleService implements IAccountRoleService {

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
}
