package com.xchat.xchat.email;

import com.xchat.xchat.cache.AppCache;
import com.xchat.xchat.cache.ConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class EmailController {

    @Autowired
    private final EmailService emailService;


    @Autowired
    public  AppCache appCache;

    @Autowired
    KafkaTemplate <String , EmailModel> kafkaTemplate;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email")
    public String sendEmail(@RequestBody EmailModel emailData ) {
        System.out.println(emailData);
        try {
            kafkaTemplate.send("vchat_notifications" , emailData.getTo() ,emailData );
        }catch (Exception e){
            emailService.sendEmail(emailData.to, emailData.subject, emailData.message);
        }

        return  "Email sent successfully";
    }


}

