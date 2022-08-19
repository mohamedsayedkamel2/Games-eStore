package com.store.videogames.service.customer.account;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Roles;
import com.store.videogames.exceptions.exception.MessageEncodingErrorException;
import com.store.videogames.exceptions.exception.EmailUnknownErrorException;
import com.store.videogames.repository.CustomerRepository;
import com.store.videogames.repository.RolesRepository;
import com.store.videogames.service.customer.CustomerInformationRetriverService;
import com.store.videogames.util.common.PasswordEncoder;
import com.store.videogames.util.common.WebsiteUrlGetter;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
//If any exception happens just rollback
@Transactional(rollbackOn = {Exception.class})
public class CustomerRegistrationService
{
    @Autowired
    private CustomerInformationRetriverService customerInformationRetriverService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CustomerEmailService customerEmailService;

    //Customer registration process function
    @CachePut("Customer")
    public void registerCustomer(Customer customer, HttpServletRequest httpServletRequest)
    {
        setCustomerData(customer);
        customerInformationRetriverService.saveCustomerIntoDB(customer);
        sendVerificationEmail(httpServletRequest,customer);
    }

    void setCustomerData(Customer customer)
    {
        //First we will set the user registration date and time
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        //Second the user password will be encrypted
        customer.setPassword(PasswordEncoder.getBcryptPasswordEncoder().encode(customer.getPassword()));
        //Third step make a randomToken for the email verification and inject it in the customer object
        String randomCode = RandomString.make(64);
        customer.setEmailVerificationCode(randomCode);
        //Fourth step mark the user as NOT enabled because the user didn't verfiy his email
        customer.setEnabled(false);
        //Fifth step set default user role to USER
        Roles role = rolesRepository.getRolesByName("USER");
        List<Roles> roles = new ArrayList<>();
        roles.add(role);
        customer.setRoles(roles);
        customer.setBalance(100);
    }

    void sendVerificationEmail(HttpServletRequest request, Customer customer)
    {
        //This variable will store the website url and send it to send Verification Email function
        String websiteUrl = WebsiteUrlGetter.getSiteURL(request);
        try
        {
            customerEmailService.sendVerificationEmail(customer, websiteUrl);
        }
        catch (MessagingException e)
        {
            throw new EmailUnknownErrorException("Error occured while sending an email to the customer");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new MessageEncodingErrorException("Error occured while sending an email to the customer");
        }
    }


}