package com.store.videogames.service.payment.impl.strategy;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Order;
import com.store.videogames.entites.Videogame;
import com.store.videogames.service.customer.CustomerMoneyHistorySaver;
import com.store.videogames.service.customer.CustomerInfoRetriver;
import com.store.videogames.service.payment.IPaymentService;
import com.store.videogames.service.payment.order.OrderService;
import com.store.videogames.service.videogame.VideogameRetrivingService;
import com.store.videogames.service.videogame.VideogameUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PhysicalPaymentServiceStrategy implements IPaymentService
{
    private final Logger logger = LoggerFactory.getLogger(PhysicalPaymentServiceStrategy.class);

    private CustomerMoneyHistorySaver customerMoneyHistorySaver;
    private CustomerInfoRetriver customerInfoRetriver;
    private VideogameRetrivingService videogameRetrivingService;
    private VideogameUpdateService videogameUpdateService;
    private OrderService orderService;

    @Autowired
    public PhysicalPaymentServiceStrategy(CustomerMoneyHistorySaver customerMoneyHistorySaver,
                                          CustomerInfoRetriver customerInfoRetriver,
                                          VideogameRetrivingService videogameRetrivingService,
                                          VideogameUpdateService videogameUpdateService,
                                          OrderService orderService)
    {
        this.customerMoneyHistorySaver = customerMoneyHistorySaver;
        this.customerInfoRetriver = customerInfoRetriver;
        this.videogameRetrivingService = videogameRetrivingService;
        this.videogameUpdateService = videogameUpdateService;
        this.orderService = orderService;
    }

    @Override
    public void buyProduct(Customer customer, Videogame videogame)
    {
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = oldCustomerBalance - videogame.getPrice();
        // Update the custoemr balance
        customer.setBalance(newUserBalance);
        // Add the game to the customer's bought games list
        customer.addVideogame(videogame);

        //Update the customer record in the databse with the new data
        customerInfoRetriver.updateCustomer(customer);

        //update the videogame record in the database with the new data
        videogameUpdateService.storeNewVideogame(videogame);

        //create an order and a history record of the user balance before and after the payment
        Order order = orderService.createOrder(customer,videogame);
        customerMoneyHistorySaver.presistPaymentRecord(order, oldCustomerBalance,newUserBalance);

        // Send Physical Order Mail
        String mailSubject = "";
        String mailBody = "";
        orderService.sendOrderMail(order.getCustomer().getEmail(), mailSubject, mailBody);
    }
}