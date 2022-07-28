package com.store.videogames.service.customer.account;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.util.interfaces.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class CustomerEmailService
{
    //To avoid a bean dependancy cycle I will use CustomerRepository instead of CustomerService
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException
    {
        String toAddress = customer.getEmail();
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

        content = content.replace("[[name]]", customer.getUsername());
        String verifyURL = siteURL + "/verify?code=" + customer.getEmailVerificationCode();

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
