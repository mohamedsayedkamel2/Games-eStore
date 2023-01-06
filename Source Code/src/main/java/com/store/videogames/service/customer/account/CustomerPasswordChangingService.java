package com.store.videogames.service.customer.account;

import com.store.videogames.entites.Customer;
import com.store.videogames.exceptions.exception.InvalidPasswordRestToken;
import com.store.videogames.service.customer.CustomerInfoRetriver;
import com.store.videogames.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerPasswordChangingService
{
    private final CustomerInfoRetriver customerInfoRetriver;

    @Autowired
    public CustomerPasswordChangingService(CustomerInfoRetriver customerInfoRetriver)
    {
        this.customerInfoRetriver = customerInfoRetriver;
    }

    public void updateResetPasswordToken(String token, String email)
    {
        Customer customer = customerInfoRetriver.getCustomerbyEmail(email);
        if (customer != null)
        {
            customer.setResetPasswordToken(token);
            customerInfoRetriver.updateCustomer(customer);
        }
        else
        {
            // If the customer wasn't found in the database
            throw new InvalidPasswordRestToken("Email Not Found");
        }
    }

    public void updatePassword(Customer customer, String newPassword)
    {
        String encodedPassword = PasswordEncoder.getBcryptPasswordEncoder().encode(newPassword);
        customer.setPassword(encodedPassword);
        customer.setResetPasswordToken(null);
        customerInfoRetriver.updateCustomer(customer);
    }
}