package com.store.videogames.service.customer.payment;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Videogame;

import javax.mail.MessagingException;


public interface IPaymentService
{
    void buyProduct(Customer customer, Videogame videogame) throws MessagingException;
}
