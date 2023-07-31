package com.example.vaccination_management.repository;

import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {

    @Transactional
    @Modifying
    @Query(value="INSERT INTO account (username, password, verification_code, email, enable_flag)" +
            "VALUE(?1,?2,?3,?4,?5)", nativeQuery = true)
    void insertAccount(String name, String password, String verificationCode, String email, Boolean enableFlag);

    @Query(value = "SELECT MAX(id) FROM account", nativeQuery = true)
    Integer findLatestAccountId();


    @Query(value = "SELECT account.id, account.verification_code AS accountVerificationCode, account.password, account.email, patient.name AS patientName, patient.birthday, patient.gender, patient.address, patient.phone, patient.guardian_name AS patientGuardianName, patient.guardian_phone AS patientGuardianPhone, patient.detele_flag AS patientDeleteFlag, account.enable_flag AS accountEnableFlag " +
            "FROM account JOIN patient ON patient.account_id = account.id " +
            "WHERE account.id = ?1", nativeQuery = true)
    IAccountDetailDTO findAccountById(Integer id);;
    //"AS" để đổi tên cho trường hiển thị trường dữ liệu trở nên rõ ràng hơn và tránh được sự mâu thuẫn
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET enable_flag = ?1 WHERE account.id = ?2",nativeQuery = true)
    void updateEnableFlagById(Boolean enableFlag, Integer id);



}
