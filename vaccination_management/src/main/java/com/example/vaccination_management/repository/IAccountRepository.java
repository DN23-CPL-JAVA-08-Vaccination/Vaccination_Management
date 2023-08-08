package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    /**
     * ThangLV
     * Insert new Account of Employee
     */
    @Modifying
    @Query(value = "INSERT INTO account (email, enable_flag, password, username) VALUES (?,?,?,?)", nativeQuery = true)
    void insertAccount(String email, boolean enableFlag, String password, String username);

    /**
     * ThangLV
     * Update Account of Employee
     */
    @Modifying
    @Query(value = "UPDATE account SET email = ?1, username = ?2 WHERE (id = ?3);", nativeQuery = true)
    void updateAccount(String email, String username, Integer id);

    /**
     * ThangLV
     * get Id of Account By UserName
     */
    @Query(value = "select a.id from account a where a.username = ?", nativeQuery = true)
    Integer findIdUserByUserName(String userName);

    /**
     * ThangLV
     * get  Account By UserName
     */
    @Query(value = "SELECT * FROM account where username = ?1", nativeQuery = true)
    Optional<Account> findAccountByUserName(String username);

    /**
     * ThangLV
     * get current password
     */
    @Query(value = "select password from account where username = ?", nativeQuery = true)
    String getCurrentPassword(String userName);

    /**
     * ThangLV
     * change password
     */
    @Modifying
    @Query(value = "update account set password =?1 where username=?2 ", nativeQuery = true)
    void changePassword(String password, String userName);
}
