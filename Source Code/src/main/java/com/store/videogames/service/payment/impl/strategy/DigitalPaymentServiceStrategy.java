package com.store.videogames.service.payment.impl.strategy;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Order;
import com.store.videogames.entites.Videogame;
import com.store.videogames.repository.DigitalVideogameCodeRepository;
import com.store.videogames.service.customer.CustomerMoneyHistoryService;
import com.store.videogames.service.customer.CustomerInformationRetriverService;
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
public class DigitalPaymentServiceStrategy implements IPaymentService
{
    private final Logger logger = LoggerFactory.getLogger(DigitalPaymentServiceStrategy.class);

    @Autowired
    private DigitalVideogameCodeRepository digitalVideogameCodeRepository;
    @Autowired
    private CustomerMoneyHistoryService customerMoneyHistoryService;
    @Autowired
    private CustomerInformationRetriverService customerInformationRetriverService;
    @Autowired
    private VideogameRetrivingService videogameRetrivingService;
    @Autowired
    private VideogameUpdateService videogameUpdateService;
    @Autowired
    private OrderService orderService;

    @Transactional
    public void buyProduct(Customer customer, Videogame videogame)
    {
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = oldCustomerBalance - videogame.getPrice();
        customer.setBalance(newUserBalance);
        customer.addVideogame(videogame);

        //update the customer record in the databse with the new data
        customerInformationRetriverService.saveCustomerIntoDB(customer);

        //update the videogame record in the database with the new data
        videogameUpdateService.storeNewVideogame(videogame);

        //create an order and a history record of the user balance before and after the payment
        Order order = orderService.createOrder(customer,videogame);
        customerMoneyHistoryService.createRecord(order, oldCustomerBalance,newUserBalance);

        // Send Digital Order Mail
        String mailSubject = "THANKS FOR BUYING A DIGITAL PRODUCT";
        String mailBody = "THE PRODUCT NAME IS " + order.getVideogame().getGameName();
        orderService.sendOrderMail(order.getCustomer().getEmail(), mailSubject, mailBody);
    }
}
