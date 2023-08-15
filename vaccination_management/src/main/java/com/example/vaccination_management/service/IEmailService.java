package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Vaccination;

import java.util.List;

public interface IEmailService {


    Boolean SendEmail(IAccountDetailDTO detailDTO);

    Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO);

    Boolean SendEmailForgotPassword(AccountDTO accountDTO);

    String hideEmail(String email);
    public Boolean SendEmailTBByLocation(Vaccination vaccination);

//    public List<String> getPatientsWithMatchingLocationName(Vaccination vaccination);
//public Boolean SendEmailTBByLocation(Vaccination vaccination);
}
