package com.xchat.xchat.email;

import com.xchat.xchat.cache.AppCache;
import com.xchat.xchat.cache.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class EmailController {

    @Autowired
    private final EmailService emailService;


    @Autowired
    public  AppCache appCache;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/email")
    public String sendEmail() {
        emailService.sendEmail("veekshithm@gmail.com", "subject", "body");
        return "Email sent successfully!";
    }


}

