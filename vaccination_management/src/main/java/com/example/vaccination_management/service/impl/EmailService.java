package com.example.vaccination_management.service.impl;

import com.example.vaccination_management.dto.DataMailDTO;
import com.example.vaccination_management.entity.Account;
import com.example.vaccination_management.repository.IAccountRepository;
import com.example.vaccination_management.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.ui.Model;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    //test email
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Enable HTML content if needed

            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            e.printStackTrace(); // Handle or log the exception accordingly
        }
    }
}
//    @Override
//    public void sendEmailById(int userId, String subject, String content) {
//        Account user = iAccountRepository.findById(userId).orElse(null);
//        if (user != null) {
//            String email = user.getEmail();
//
//            // Gửi email với thông tin nhận được từ đối tượng User
//            sendEmail(email, subject, content);
//        }
//    }
//}
////test email

//
//    private void sendEmails(String to, String subject, String content) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(content);
//        mailSender.send(message);
//    }
//
//    //test email
//}
//
//

    //error
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    @Override
//    public void sendHtmlMail(DataMailDTO dataMail, String templateName) throws MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
//
//        Context context = new Context();
//        context.setVariables(dataMail.getProps());
//
//        String html = templateEngine.process(templateName, context);
//
//        helper.setTo(dataMail.getTo());
//        helper.setSubject(dataMail.getSubject());
//        helper.setText(html, true);
//
//        mailSender.send(message);
//    }
