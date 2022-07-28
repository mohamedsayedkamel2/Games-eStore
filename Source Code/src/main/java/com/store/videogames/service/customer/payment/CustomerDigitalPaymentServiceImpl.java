package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.DigitalVideogameCodeRepository;
import com.store.videogames.service.customer.CustomerMoneyHistoryService;
import com.store.videogames.service.customer.CustomerService;
import com.store.videogames.service.customer.interfaces.ICustomerPaymentSerivce;
import com.store.videogames.service.videogame.VideogameRetrivingService;
import com.store.videogames.service.videogame.VideogameUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

@Service
public class CustomerDigitalPaymentServiceImpl implements ICustomerPaymentSerivce
{
    @Autowired
    private DigitalVideogameCodeRepository digitalVideogameCodeRepository;
    @Autowired
    private CustomerMoneyHistoryService customerMoneyHistoryService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VideogameRetrivingService videogameRetrivingService;
    @Autowired
    private VideogameUpdateService videogameUpdateService;
    @Autowired
    private OrderService orderService;

    @Transactional
    public boolean buyProduct(Customer customer, float overallPrice, Videogame videogame) throws MessagingException
    {
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = customer.getBalance() - overallPrice;
        customer.setBalance(newUserBalance);
        customer.addVideogame(videogame);
        try
        {
            //update the customer record in the databse with the new data
            customerService.saveCustomerIntoDB(customer);
        }
        catch (Exception exception)
        {
            System.out.println("Error happened while updating the new customer data");
            return false;
        }

        try
        {
            //update the videogame record in the database with the new data
            videogameUpdateService.storeNewVideogame(videogame);
        }
        catch (Exception exception)
        {
            System.out.println("Error happened whle updating the new video game data");
            return false;
        }
        //create an order and a history record of the user balance before and after the payment
        Order order = orderService.createOrder(customer,videogame);
        try
        {
            customerMoneyHistoryService.createRecord(order, oldCustomerBalance,newUserBalance);
        }
        catch (Exception exception)
        {
            System.out.println("Error happened while creating a record");
            return false;
        }
        orderService.sendDigitalOrderMail(order);
        return true;
    }
}
