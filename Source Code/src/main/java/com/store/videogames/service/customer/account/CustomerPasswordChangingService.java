package com.store.videogames.service.customer.account;

import com.store.videogames.entites.Customer;
import com.store.videogames.exceptions.exception.InvalidPasswordRestToken;
import com.store.videogames.service.customer.CustomerInformationRetriverService;
import com.store.videogames.util.common.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerPasswordChangingService
{
    private final CustomerInformationRetriverService customerInformationRetriverService;

    @Autowired
    public CustomerPasswordChangingService(CustomerInformationRetriverService customerInformationRetriverService)
    {
        this.customerInformationRetriverService = customerInformationRetriverService;
    }

    public void updateResetPasswordToken(String token, String email)
    {
        Customer customer = customerInformationRetriverService.getCustomerbyEmail(email);
        if (customer != null)
        {
            customer.setResetPasswordToken(token);
            customerInformationRetriverService.updateCustomer(customer);
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
        customerInformationRetriverService.updateCustomer(customer);
    }
}