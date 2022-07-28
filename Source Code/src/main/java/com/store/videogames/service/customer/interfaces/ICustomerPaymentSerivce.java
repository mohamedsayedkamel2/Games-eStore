package com.store.videogames.service.customer.interfaces;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.DigitalVideogameCode;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;

import javax.mail.MessagingException;


public interface ICustomerPaymentSerivce
{
    //putting @Transactional here is a bad practice, it's advised to put in on the concrete class
 boolean buyProduct(Customer customer, float overallPrice, Videogame videogame) throws MessagingException;

 default boolean isBalanceSufficent(float videogamePrice, float customerBalance)
 {
    if (customerBalance < videogamePrice)
    {
       System.out.println("Balance not enough");
       return false;
    }
    return true;
 }
}
