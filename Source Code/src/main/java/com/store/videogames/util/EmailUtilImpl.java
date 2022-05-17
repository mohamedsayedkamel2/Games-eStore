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
public class EmailUtilImpl implements EmailUtil
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendEmail(String toEmailAddress, String subject, String body) throws MessagingException
    {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);
        mimeMessageHelper.setTo(toEmailAddress);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        javaMailSender.send(message);
    }
}
