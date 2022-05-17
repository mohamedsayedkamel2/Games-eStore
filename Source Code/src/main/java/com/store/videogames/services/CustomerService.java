package com.store.videogames.services;

import com.store.videogames.config.PasswordEncoder;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.services.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CustomerService implements ICustomerService
{
    @Autowired
    CustomerRepository customerRepository;

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
/*Start of forgot-password feature code*/
    @Override
    public Customer getCustomerByResetPasswordToken(String token)
    {
        return customerRepository.getCustomerByResetPasswordToken(token);
    }

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
        String encodedPassword = PasswordEncoder.bCryptPasswordEncoder(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }
/*End of forget-password feature code*/

}