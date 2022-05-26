package com.store.videogames.service.customer;

import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.interfaces.CustomerMoneyHistoryRepository;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMoneyHistoryRepository customerMoneyHistoryRepository;

    public void saveCustomerIntoDB(Customer customer)
    {
        customerRepository.save(customer);
    }

    public Customer getCustomerbyEmail(String email)
    {
        return customerRepository.getCustomerByEmail(email);
    }

    public Customer getCustomerByUsername(String username)
    {
        return customerRepository.getCustomerByUsername(username);
    }

    public List<Customer> getCustomersByCountryName(String countryName)
    {
        return customerRepository.getCustomerByCountryName(countryName);
    }

    public List<Customer> getCustomersByCityName(String cityName)
    {
        return customerRepository.getCustomerByCityName(cityName);
    }

    public List<Customer> getCustomersByStreetName(String streetName)
    {
        return customerRepository.getCustomerByStreetName(streetName);
    }

    public List<Customer> getCustomersByZipCode(int zipCode)
    {
        return customerRepository.getCustomerByZipCode(zipCode);
    }

    public List<Customer> getCustomersByRegistrationDate(LocalDate registrationDate)
    {
        return customerRepository.getCustomerByRegistrationDate(registrationDate);
    }

    public List<Customer> getCustomersByRegistrationTime(LocalTime registrationTime)
    {
        return customerRepository.getCustomerByRegistrationTime(registrationTime);
    }

    public List<Customer> getCustomersByEnabled(Boolean isEnabled)
    {
        return customerRepository.getCustomerByEnabled(isEnabled);
    }

    public Customer getCustomerByEmailVerificationCode(String EmailVerificationCode)
    {
        return customerRepository.getCustomerByEmailVerificationCode(EmailVerificationCode);
    }
    public Customer getCustomerByResetPasswordToken(String token)
    {
        return customerRepository.getCustomerByResetPasswordToken(token);
    }

    public CustomerMoneyHistory getMoneyHistoryByOrder(Order order)
    {
        return customerMoneyHistoryRepository.getCustomerMoneyHistoryByOrder(order);
    }
}