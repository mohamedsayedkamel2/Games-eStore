package com.store.videogames.repository;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.repository.interfaces.OrderRepository;
import com.store.videogames.repository.interfaces.VideogameRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;


@SpringBootTest
public class OrderRepositoryTest
{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VideogameRepository videogameRepository;

    @Test
    public void getAllOrders()
    {
        orderRepository.findAll();
    }

    @Test
    public void createOrder()
    {
        Customer customer = customerRepository.getById(1);
        Videogame videogame = videogameRepository.getById(1);
        Order order = new Order();
        order.setCustomer(customer);
        order.setId(1);
        order.setDigitalVideogameCode(RandomString.make(40));
        order.setPurchaseDate(LocalDate.now());
        order.setPurchaseTime(LocalTime.now());
        order.setVideogame(videogame);
        order.setOrderTransaction(RandomString.make(50));
        orderRepository.save(order);
    }

    @Test
    public void deleteOrder()
    {
        orderRepository.deleteById(5);
    }
}
