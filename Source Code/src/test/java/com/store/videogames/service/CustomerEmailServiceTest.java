package com.store.videogames.service;

import com.store.videogames.entites.Customer;
import com.store.videogames.repository.CustomerRepository;
import com.store.videogames.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@SpringBootTest
public class CustomerEmailServiceTest
{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail() throws MessagingException, UnsupportedEncodingException
    {
        String toAddress = "msayedkamel68@gmail.com";
        String fromAddress = "momosayed057@gmail.com";
        String senderName = "Your company name";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", "mohamed");
        String verifyURL = "localhost" + "/verify?code=" + "123456789";

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    public boolean verify(String verificationCode)
    {
        Customer customer = customerRepository.getCustomerByEmailVerificationCode(verificationCode);
        System.out.println(customer);
        System.out.println(customer.getEmailVerificationCode());
        if (customer == null || customer.isEnabled())
        {
            return false;
        }
        else
        {
            customer.setEmailVerificationCode(null);
            customer.setEnabled(true);
            customerRepository.save(customer);
            return true;
        }
    }
}
