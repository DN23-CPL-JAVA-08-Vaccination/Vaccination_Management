package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAccountRepository extends JpaRepository<Account, Integer> {
//    @Query(value = "select ")
//  Account findByName(String name);
//   List<Account> findByAddress(String address);

}
