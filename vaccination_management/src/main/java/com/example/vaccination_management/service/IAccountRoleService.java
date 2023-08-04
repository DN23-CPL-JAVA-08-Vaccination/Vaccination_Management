package com.example.vaccination_management.service;

public interface IAccountRoleService {

    /**
     * ThangLV
     * insert account role used to decentralize
     */
    void insertAccountRole(Integer accountId, Integer role);

    /**
     * ThangLV
     * authenticate Account
     */
    Boolean checkPassword(String password, String username);
}
