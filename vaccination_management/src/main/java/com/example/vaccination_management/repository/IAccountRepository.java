package com.example.vaccination_management.repository;

import com.example.vaccination_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.example.vaccination_management.dto.IAccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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

    /**
     * TLINH
     * Insert account
     */
    @Transactional
    @Modifying
    @Query(value="INSERT INTO account (username, password, email, enable_flag)" +
            "VALUE(?1,?2,?3,?4)", nativeQuery = true)
    void insertAccount(String name, String password, String email, Boolean enableFlag);


    /**
     * TLINH
     * Find the largest id value
     */
    @Query(value = "SELECT MAX(id) FROM account", nativeQuery = true) //tìm đến id lớn nhất
    Integer findLatestAccountId();


    /**
     * TLINH
     * Join table show detail account
     */
    @Query(value = "SELECT account.id, patient.health_insurance AS patientHealthInsurance, account.password, account.email, patient.name AS patientName, patient.birthday, patient.gender, patient.address, patient.phone, patient.guardian_name AS patientGuardianName, patient.guardian_phone AS patientGuardianPhone, patient.detele_flag AS patientDeleteFlag, account.enable_flag AS accountEnableFlag " +
            "FROM account JOIN patient ON patient.account_id = account.id " +
            "WHERE account.id = ?1", nativeQuery = true)
    IAccountDetailDTO findAccountById(Integer id);;
    //"AS" để đổi tên cho trường hiển thị trường dữ liệu trở nên rõ ràng hơn và tránh được sự mâu thuẫn


    /**
     * TLINH
     * Update enableFlag by id
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET enable_flag = ?1 WHERE account.id = ?2",nativeQuery = true)
    void updateEnableFlagById(Boolean enableFlag, Integer id);



    /**
     * TLINH
     * Count the number of account usernames
     */
    @Query(value = "select count(username) from account where username = ?1", nativeQuery = true)
    Integer finByUserName(String userName);


    /**
     * TLINH
     * find account by username
     */
    @Query(value = "SELECT*FROM account WHERE account.username=?1",nativeQuery = true)
    IAccountDTO findAllByUsername(String userName);


    /**
     * TLINH
     * update verification code in table account bt username
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET verification_code = ?1 WHERE account.username = ?2",nativeQuery = true)
    void updateVerificationCodeByUserName(String verificationCode, String userName);


    /**
     * TLINH
     * Count the number of table account verification code
     */
    @Query(value = "SELECT count(verification_code) FROM account WHERE verification_code = ?1",nativeQuery = true)
    Integer checkVerificationCode(String verificationCode);



    /**
     * TLINH
     * Update password in table account by verification code
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET password = ?1 WHERE account.verification_code = ?2", nativeQuery = true)
    void rePasswordByVerificationCode(String password, String verificationCode);


    /**
     * TLINH
     * pagination and search
     */
    @Query(value = "SELECT * FROM account WHERE account.username LIKE ?1",nativeQuery = true)
    Page<Account> findAccountListPage(String userName, Pageable pageable);


    /**
     * TLINH
     * Count the number of id by username
     */
    @Query(value = "SELECT count(account.id) FROM account WHERE account.username LIKE ?", nativeQuery = true)
    long getTotalAccount(String userName);

}
