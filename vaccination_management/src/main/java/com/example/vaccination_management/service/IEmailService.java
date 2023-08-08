package com.example.vaccination_management.service;

import com.example.vaccination_management.dto.DataMailDTO;

import javax.mail.MessagingException;

public interface IEmailService {
    public void sendEmail(String to, String subject, String content);


    //Error
    //public void sendEmailById(int userId, String subject, String content);
//    void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}
