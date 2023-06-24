package com.l4plan.api.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static String changePassMailBody = "Hola,\nMuchas gracias por usar nuestros servicios. Su nueva contraseña automaticamente generada es: %s\nUn cordial saludo,\nEquipo de L4Plan.";
    private static String changePassMailSubject = "Se ha cambiado su contraseña de L4Plan";

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailChangingPass(String email, String password) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(changePassMailSubject);
        message.setText(String.format(changePassMailBody, password));
        mailSender.send(message);
    }
}
