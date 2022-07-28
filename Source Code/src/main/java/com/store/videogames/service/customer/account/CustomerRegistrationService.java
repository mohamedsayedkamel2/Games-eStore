package com.store.videogames.service.customer.account;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.entites.enums.AuthenticationProvider;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.repository.interfaces.RolesRepository;
import com.store.videogames.service.customer.CustomerService;
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
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private CustomerEmailService customerEmailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Customer registration process function
    @CachePut("Customer")
    public boolean registerCustomer(Customer customer, HttpServletRequest httpServletRequest) throws MessagingException, UnsupportedEncodingException
    {
        setCustomerData(customer);
        customerService.saveCustomerIntoDB(customer);
        sendVerificationEmail(httpServletRequest,customer);
        return true;
    }

    void setCustomerData(Customer customer)
    {
        //First we will set the user registration date and time
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        //Second the user password will be encrypted
        customer.setPassword(passwordEncoder.getBcryptPasswordEncoder().encode(customer.getPassword()));
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
        customer.setBalance(10000);
    }

    void sendVerificationEmail(HttpServletRequest request, Customer customer) throws MessagingException, UnsupportedEncodingException
    {
        //This variable will store the website url and send it to send Verification Email function
        String websiteUrl = WebsiteUrlGetter.getSiteURL(request);
        customerEmailService.sendVerificationEmail(customer, websiteUrl);
    }

    public void updatePassword(Customer customer, String newPassword)
    {
        String encodedPassword = passwordEncoder.getBcryptPasswordEncoder().encode(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }

    /*Start of forgot-password feature code*/
    public void updateResetPasswordToken(String token, String email)
    {
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer != null)
        {
            customer.setResetPasswordToken(token);
            customerRepository.save(customer);
        }
    }
}
