package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;

public interface IEmailService {

    void sendEmail(String to, String subject, String content);

    Boolean SendEmail(IAccountDetailDTO detailDTO);

    Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO);

    Boolean SendEmailForgotPassword(AccountDTO accountDTO);

    String hideEmail(String email);
}
