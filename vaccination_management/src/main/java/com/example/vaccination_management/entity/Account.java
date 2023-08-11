
package com.example.vaccination_management.entity;


import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "email")
    private String email;

    @Column(name = "enable_flag")
    private Boolean enableFlag;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<AccountRole> accountRoles;


    public Account() {
    }


    public Account(int id, String userName, String password, String verificationCode, String email, Boolean enableFlag, List<AccountRole> accountRoles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.verificationCode = verificationCode;
        this.email = email;
        this.enableFlag = enableFlag;
        this.accountRoles = accountRoles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public List<AccountRole> getAccountRoles() {
        return accountRoles;
    }

    public void setAccountRoles(List<AccountRole> accountRoles) {
        this.accountRoles = accountRoles;
    }


}
