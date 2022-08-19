package com.store.videogames.service.customer.payment.order;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Order;
import com.store.videogames.entites.Videogame;
import com.store.videogames.exceptions.exception.EmailUnknownErrorException;
import com.store.videogames.repository.OrderRepository;
import com.store.videogames.util.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OrderService
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmailUtil emailUtil;

    @Transactional
    public Order createOrder(Customer customer, Videogame videogame)
    {
        Order order = new Order();
        order.setOrderTransaction(RandomString.make(64));
        order.setCustomer(customer);
        order.setPurchaseDate(LocalDate.now());
        order.setPurchaseTime(LocalTime.now());
        order.setVideogame(videogame);
        orderRepository.save(order);
        return order;
    }

    public void sendOrderMail(String customerEmail, String mailSubject,String mailBody)
    {
        try
        {
            emailUtil.sendEmail(customerEmail, mailSubject,mailBody);
        }
        catch (MessagingException e)
        {
            throw new EmailUnknownErrorException("Execption happend while sending mail to user " + e.getCause());
        }
    }

}
