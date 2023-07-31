package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.EmailDTO;
import com.example.vaccination_management.dto.IAccountDetailDTO;

import javax.mail.MessagingException;

public interface IEmailService {

    Boolean SendEmail(IAccountDetailDTO detailDTO);

    Boolean SendEmailDeactivate(IAccountDetailDTO detailDTO);
}
