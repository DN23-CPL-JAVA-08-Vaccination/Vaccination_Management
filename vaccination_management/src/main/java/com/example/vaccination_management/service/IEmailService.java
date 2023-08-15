package com.example.vaccination_management.service;


import com.example.vaccination_management.dto.AccountDTO;
import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;


import com.example.vaccination_management.dto.IAccountDetailDTO;
import com.example.vaccination_management.entity.Vaccination;

import javax.mail.MessagingException;

public interface IEmailService {

    void sendHtmlMail(EmailDTO emailDTO, String templateName) throws MessagingException;

    Boolean SendEmailCompleted(IVaccinationHistoryDTO ivaccinationHistoryDTO);

    Boolean SendEmail(IAccountDetailDTO detailDTO);

    Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO);

    Boolean SendEmailForgotPassword(AccountDTO accountDTO);

    String hideEmail(String email);

    public Boolean SendEmailTBByLocation(Vaccination vaccination);


}
