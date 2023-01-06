package com.store.videogames.service.customer.account;

import com.store.videogames.entites.Customer;
import com.store.videogames.exceptions.exception.CustomerIsAlreadyEnabledException;
import com.store.videogames.exceptions.exception.CustomerNotFoundException;
import com.store.videogames.repository.CustomerRepository;
import com.store.videogames.util.EmailUtil;
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
    private final CustomerRepository customerRepository;
    private final EmailUtil emailUtil;
    private final JavaMailSender javaMailSender;

    public CustomerEmailService(CustomerRepository customerRepository, EmailUtil emailUtil, JavaMailSender javaMailSender)
    {
        this.customerRepository = customerRepository;
        this.emailUtil = emailUtil;
        this.javaMailSender = javaMailSender;
    }

    public void sendVerificationEmail(Customer customer, String siteURL) throws MessagingException, UnsupportedEncodingException
    {
        String toAddress = customer.getEmail();
        String fromAddress = "mohamedjustplz@gmail.com";
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

    public void verify(String verificationCode)
    {
        Customer customer = customerRepository.getCustomerByEmailVerificationCode(verificationCode);
        if (customer == null)
        {
            throw new CustomerNotFoundException("Customer was not found");
        }
        else if (customer.isEnabled())
        {
            throw new CustomerIsAlreadyEnabledException("Customer is already enabled!!");
        }
        else
        {
            customer.setEmailVerificationCode(null);
            customer.setEnabled(true);
            customerRepository.save(customer);
        }
    }

    public boolean isEmailUnique(Integer id, String email)
    {
        Customer customer = customerRepository.getCustomerByEmail(email);
        //If then customer is null then the email is avaliable if it's not NULL then the email is occuiped
        if (customer == null)
        {
            return true;
        }
        boolean isNewCustomer = (id == null);
        if (isNewCustomer == true)
        {
            //There is already a customer in this email
            if (customer != null)
            {
                return false;
            }
            else
            {
                if (customer.getId() != id)
                {
                    return false;
                }
            }
        }
        return false;
    }
}
