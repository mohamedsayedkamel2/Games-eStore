package com.store.videogames.services;

import com.store.videogames.common.PasswordEncoder;
import com.store.videogames.common.WebsiteUrlGetterClass;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.services.interfaces.ICustomerService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CustomerService implements ICustomerService
{
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerEmailService customerEmailService;


    public void saveCustomerIntoDB(Customer customer)
    {
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerbyEmail(String email)
    {
        return customerRepository.getCustomerByEmail(email);
    }

    @Override
    public Customer getCustomerByUsername(String username)
    {
        return customerRepository.getCustomerByUsername(username);
    }

    @Override
    public List<Customer> getCustomersByCountryName(String countryName)
    {
        return customerRepository.getCustomerByCountryName(countryName);
    }

    @Override
    public List<Customer> getCustomersByCityName(String cityName)
    {
        return customerRepository.getCustomerByCityName(cityName);
    }

    @Override
    public List<Customer> getCustomersByStreetName(String streetName)
    {
        return customerRepository.getCustomerByStreetName(streetName);
    }

    @Override
    public List<Customer> getCustomersByZipCode(int zipCode)
    {
        return customerRepository.getCustomerByZipCode(zipCode);
    }

    @Override
    public List<Customer> getCustomersByRegistrationDate(LocalDate registrationDate)
    {
        return customerRepository.getCustomerByRegistrationDate(registrationDate);
    }

    @Override
    public List<Customer> getCustomersByRegistrationTime(LocalTime registrationTime)
    {
        return customerRepository.getCustomerByRegistrationTime(registrationTime);
    }

    @Override
    public List<Customer> getCustomersByRole(String role)
    {
        return customerRepository.getCustomerByRole(role);
    }

    @Override
    public List<Customer> getCustomersByEnabled(Boolean isEnabled)
    {
        return customerRepository.getCustomerByEnabled(isEnabled);
    }

    @Override
    public Customer getCustomerByEmailVerificationCode(String EmailVerificationCode)
    {
        return customerRepository.getCustomerByEmailVerificationCode(EmailVerificationCode);
    }
    @Override
    public Customer getCustomerByResetPasswordToken(String token)
    {
        return customerRepository.getCustomerByResetPasswordToken(token);
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

    public void updatePassword(Customer customer, String newPassword)
    {
        String encodedPassword = PasswordEncoder.getBcryptPasswordEncoder().encode(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }
    /*End of forget-password feature code*/

    //Customer registration process function
    public boolean registerCustomer(Customer customer, HttpServletRequest httpServletRequest) throws MessagingException, UnsupportedEncodingException
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
        //We will store the user into  DB then we will send the user an email which contains the verification link
        saveCustomerIntoDB(customer);
        //This variable will store the website url and send it to send Verification Email function
        String websiteUrl = WebsiteUrlGetterClass.getSiteURL(httpServletRequest);
        customerEmailService.sendVerificationEmail(customer, websiteUrl);
        return true;
    }
}