package com.store.videogames.util.impl;

import com.store.videogames.util.EmailUtil;
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

            // configure the message information (The reciver, the subject and the body of the message)
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
