package com.example.vaccination_management.dto;

public interface IAccountDTO {

    Integer getId();

    String getEmail();

    Boolean getAccountEnableFlag();

    String getPassword();

    String getUsername();

    String getVerificationCode();

    Integer getRoleId();

}
