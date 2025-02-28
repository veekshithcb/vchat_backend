package com.xchat.xchat.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    @Autowired
    private  EmailService emailService;

    @KafkaListener(topics = "vchat_notifications" , groupId = "vchat_notifications_group")
    public void consume(EmailModel emailData){

        System.out.println("fetched from kafka");
        System.out.println(emailData);
        sendEmail(emailData);
    }

    public void sendEmail(EmailModel emailData){
        emailService.sendEmail(emailData.to, emailData.subject, emailData.message);
    }


}
