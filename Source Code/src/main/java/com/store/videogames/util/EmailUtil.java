package com.store.videogames.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@EnableAsync
public class EmailUtil
{
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Note this method tightly couples the message details to the method input parameters
     * so if I wanted to add more details to the message I will have to come to this method and add more parameters
     * doing that will require me to go to many places that uses this method and change them and possible be force to send null
     * This is not scalable and I am so happy that I learned this by time
     * Solution: encaspulate the method input in an object
     */
    @Async
    public void sendEmail(String toEmailAddress, String subject, String body) throws MessagingException
    {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setTo(toEmailAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        try
        {
            javaMailSender.send(message);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
