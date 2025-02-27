package com.xchat.xchat.email;

import com.xchat.xchat.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private  AppCache appCache;

    @Bean
    public JavaMailSender javaMailSender(AppCache appCache) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);
        mailSender.setUsername(appCache.cache.get("MAIL_SENDER_USERNAME"));  // Replace with your Outlook email
        mailSender.setPassword(appCache.cache.get("MAIL_SENDER_PASSWORD")); // Replace with your Outlook password

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Use TLS
        props.put("mail.debug", "true"); // Enable debug logs (optional)

        return mailSender;
    }
}

