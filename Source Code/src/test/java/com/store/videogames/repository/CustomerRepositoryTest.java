package com.store.videogames.repository;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerRepositoryTest
{
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomers()
    {
        customerRepository.findAll();
    }

    @Test
    public void createCustomer()
    {
        Customer customer = new Customer();
        customer.setBalance(10000);
        customer.setEnabled(true);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setId(10000);
        customer.setPassword("randomPassword");
        customer.setRoles(null);
        customer.setUsername("username");
        customerRepository.save(customer);
    }

    @Test
    public void removeCustomer()
    {
        customerRepository.deleteById(2);
    }
}
