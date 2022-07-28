package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.DigitalVideogameCode;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.OrderRepository;
import com.store.videogames.util.interfaces.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    @Bean
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

    public void sendDigitalOrderMail(Order order) throws MessagingException
    {
        String subject =  "Thanks for buying " + order.getVideogame().getGameName();
        String body = "The game code is: " + order.getDigitalVideogameCode() + "     You have bought the game at: " + order.getPurchaseDate()
                +" The transaction ID is: " + order.getOrderTransaction();
        emailUtil.sendEmail(order.getCustomer().getEmail(), subject,body);
    }

    public void sendPhysicalOrderMail(Order order) throws MessagingException
    {
        String subject = "Thanks for buying " + order.getVideogame().getGameName();
        String body = "The game will arrive bettwen 5 - 7 days " +
                "Transaction ID: " + order.getOrderTransaction()  +
                " Game name: " + order.getVideogame().getGameName() +
                " Price: " + order.getVideogame().getPrice() +
                " Purchase Date: " + order.getPurchaseDate();
        emailUtil.sendEmail(order.getCustomer().getEmail(), subject,body);
    }
}
