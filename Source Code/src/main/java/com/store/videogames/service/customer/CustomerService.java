package com.store.videogames.service.customer;

import com.store.videogames.exceptions.exception.CustomerNotFoundException;
import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.interfaces.CustomerMoneyHistoryRepository;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
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

    @Transactional
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

    public List<Customer> getAll()
    {
        return customerRepository.findAll();
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

    public Customer getCustomerById(int id) throws CustomerNotFoundException
    {
        try
        {
            return customerRepository.getById(id);
        }
        catch(EntityNotFoundException exception)
        {
            throw new CustomerNotFoundException("The customer has not been found");
        }
    }

    public void updateCustomerEnabled(int id, boolean newEnableStatus)
    {
        customerRepository.updateEnabledStatus(id,newEnableStatus);
    }
}