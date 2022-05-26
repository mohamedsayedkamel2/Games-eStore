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
    public boolean registerCustomer(Customer customer, HttpServletRequest httpServletRequest) throws MessagingException, UnsupportedEncodingException
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
        //We will store the user into  DB then we will send the user an email which contains the verification link
        customerService.saveCustomerIntoDB(customer);
        //This variable will store the website url and send it to send Verification Email function
        String websiteUrl = WebsiteUrlGetter.getSiteURL(httpServletRequest);
        customerEmailService.sendVerificationEmail(customer, websiteUrl);
        return true;
    }

    public void registerCustomerOAuth(String name, String email, AuthenticationProvider authenticationProvider)
    {
        Customer customer = new Customer();
        customer.setFirstName(name);
        customer.setLastName("");
        customer.setEmail(email);
        customer.setPassword("");
        Roles role = rolesRepository.getRolesByName("USER");
        List<Roles> rolesList = new ArrayList<>();
        rolesList.add(role);
        customer.setRoles(rolesList);
        customer.setEnabled(true);
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        customer.setCountryName("");
        customer.setCityName("");
        customer.setStreetName("");
        customer.setUsername("");
        customer.setAuthenticationProvider(authenticationProvider);
        customer.setBalance(1000);
        customerRepository.save(customer);
    }

    public void updateCustomerOAuth(Customer customer, String name, AuthenticationProvider authenticationProvider)
    {
        customer.setFirstName(name);
        customer.setAuthenticationProvider(authenticationProvider);
        customerRepository.save(customer);
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
