package com.sama.portfolio.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendAcknowledgement(String toEmail, String name) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("We received your message!");

        message.setText(
                "Hi " + name + ",\n\n" +
                "Thank you for reaching out. I have received your message and will get back to you soon.\n\n" +
                "Best Regards,\n" +
                "Sama"
        );

        mailSender.send(message);
    }
}