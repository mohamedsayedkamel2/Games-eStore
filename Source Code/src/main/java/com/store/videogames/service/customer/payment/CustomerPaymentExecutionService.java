package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.service.customer.interfaces.ICustomerPaymentSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.logging.Logger;

@Service
public class CustomerPaymentExecutionService
{
    @Autowired
    ICustomerPaymentSerivce customerPaymentSerivce;

    @Transactional
    public boolean buyGame(Customer customer, float overallPrice, Videogame videogame, boolean isDigitalOrder) throws MessagingException
    {
        checkIfDigital(isDigitalOrder);

        boolean isBalanceSufficent = customerPaymentSerivce.isBalanceSufficent(videogame.getPrice(), customer.getBalance());
        if (!isBalanceSufficent)
        {
            System.out.println("Unsufficent balance");
            return false;
        }

        boolean isPaymentSuccess = customerPaymentSerivce.buyProduct(customer, overallPrice, videogame);
        if (!isPaymentSuccess)
        {
            System.out.println("Error happened while payment process");
            return false;
        }

        return true;
    }

    private void checkIfDigital(boolean isDigitalOrder)
    {
        if (isDigitalOrder)
        {
            customerPaymentSerivce = setPaymentServiceToDigital();
        }
    }

    @Bean
    private ICustomerPaymentSerivce setPaymentServiceToDigital()
    {
        return new CustomerDigitalPaymentServiceImpl();
    }
}
