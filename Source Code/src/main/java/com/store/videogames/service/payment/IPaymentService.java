package com.store.videogames.service.payment;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Videogame;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;


@Component
public interface IPaymentService
{
    void buyProduct(Customer customer, Videogame videogame) throws MessagingException;
}
