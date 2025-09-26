package com.financeAssitant.FinBrains.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String firstName, String verificationToken) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Verify Your Finance Assistant Account");
            message.setText(String.format(
                    "Hello %s,\n\n" +
                            "Welcome to Finance Assistant! Please verify your email address by clicking the link below:\n\n" +
                            "%s/verify-email?token=%s\n\n" +
                            "This link will expire in 24 hours.\n\n" +
                            "If you didn't create this account, please ignore this email.\n\n" +
                            "Best regards,\n" +
                            "Finance Assistant Team",
                    firstName, frontendUrl, verificationToken
            ));
            message.setFrom("noreply@financeassistant.com");

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            // Don't throw exception - allow signup to continue even if email fails
        }
    }
}