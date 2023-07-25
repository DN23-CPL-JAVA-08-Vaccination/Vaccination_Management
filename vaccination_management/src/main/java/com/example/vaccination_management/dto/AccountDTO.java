package com.example.vaccination_management.dto;

import lombok.*;


public class AccountDTO {
    private Integer id;
    private String username;
    private String verificationCode;

    private String password;
    private String email;
    private Boolean enableFlag;

    public AccountDTO() {
    }

    public AccountDTO(Integer id, String username, String verificationCode, String password, String email, Boolean enableFlag) {
        this.id = id;
        this.username = username;
        this.verificationCode = verificationCode;
        this.password = password;
        this.email = email;
        this.enableFlag = enableFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
