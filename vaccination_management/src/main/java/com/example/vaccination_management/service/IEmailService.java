package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IVaccinationHistoryDTO;

import javax.mail.MessagingException;

public interface IEmailService {
    void sendHtmlMail(EmailDTO emailDTO, String templateName) throws MessagingException;

    Boolean SendEmailCompleted(IVaccinationHistoryDTO ivaccinationHistoryDTO);
}
