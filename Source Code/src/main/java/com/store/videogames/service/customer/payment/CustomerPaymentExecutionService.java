package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.service.customer.interfaces.ICustomerPaymentSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerPaymentExecutionService
{
    @Autowired
    CustomerPhysicalPaymentServiceImpl customerPhysicalPaymentService;
    @Autowired
    CustomerDigitalPaymentServiceImpl customerDigitalPaymentService;
    @Autowired
    ICustomerPaymentSerivce customerPaymentSerivce;

    @Transactional(rollbackOn = Exception.class)
    public boolean buyGame(Customer customer, float overallPrice, Videogame videogame, boolean isDigitalOrder) throws MessagingException
    {
        checkIfDigital(isDigitalOrder);
        if (customerPaymentSerivce.isBalanceSufficent(videogame.getPrice(), customer.getBalance()) == false)
        {
            System.out.println("Unsufficent balance");
            return false;
        }

        if (customerPaymentSerivce.buyProduct(customer,overallPrice,videogame) == false)
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
            customerPaymentSerivce = customerDigitalPaymentService;
        }
        else
        {
            customerPaymentSerivce = customerPhysicalPaymentService;
        }
    }
}
