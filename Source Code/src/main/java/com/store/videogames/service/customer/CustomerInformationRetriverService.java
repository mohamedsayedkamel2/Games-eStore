package com.store.videogames.service.customer;

import com.store.videogames.exceptions.exception.CustomerNotFoundException;
import com.store.videogames.entites.CustomerMoneyHistory;
import com.store.videogames.entites.Order;
import com.store.videogames.repository.CustomerMoneyHistoryRepository;
import com.store.videogames.entites.Customer;
import com.store.videogames.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class CustomerInformationRetriverService
{
    private final CustomerRepository customerRepository;
    private final CustomerMoneyHistoryRepository customerMoneyHistoryRepository;

    @Autowired
    public CustomerInformationRetriverService(CustomerRepository customerRepository,
                                              CustomerMoneyHistoryRepository customerMoneyHistoryRepository)
    {
        this.customerRepository = customerRepository;
        this.customerMoneyHistoryRepository = customerMoneyHistoryRepository;
    }

    public Customer getCustomerById(int id)
    {
        try
        {
            return customerRepository.findById(id).get();
        }
        catch(EntityNotFoundException exception)
        {
            throw new CustomerNotFoundException("The customer has not been found");
        }
    }

    @CachePut("Customer")
    public void saveCustomerIntoDB(Customer customer)
    {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer)
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

    public Customer getByEmailVerificationCode(String EmailVerificationCode)
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

    @Cacheable("Customer")
    public List<Customer> getAll()
    {
        return customerRepository.findAll();
    }


}