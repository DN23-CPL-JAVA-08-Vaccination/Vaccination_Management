package com.example.vaccination_management.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IAccountRoleRepository {


    /**
     * ThangLV
     * insert new Account Role
     */
    @Modifying
    @Query(value = "insert into account_role(account_id,role_id) values (?1,?2)", nativeQuery = true)
    void insertAccountRole(Integer accountId, Integer roleId);
}
