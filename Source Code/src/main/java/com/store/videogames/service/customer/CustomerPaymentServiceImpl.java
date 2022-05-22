package com.store.videogames.service.customer;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerMoneyHistoryRepository;
import com.store.videogames.repository.interfaces.OrderRepository;
import com.store.videogames.service.videogame.VideogameService;
import com.store.videogames.util.interfaces.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class CustomerPaymentServiceImpl
{
    @Autowired
    private CustomerMoneyHistoryRepository customerMoneyHistoryRepository;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    VideogameService videogameService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EmailUtil emailUtil;

    public CustomerMoneyHistory getMoneyHistoryByOrder(Order order)
    {
        return customerMoneyHistoryRepository.getCustomerMoneyHistoryByOrder(order);
    }


    /**
     * @param customer is the customer who wants to buy the videogame
     * @param quantity is the quantity of the game the customer wants to buy
     * @param overallPrice price is the total price which is the videogame price * quantity
     * @param purchasedVideogame is the videogame the customer wants to buy
     */
    @Transactional
    public boolean buyProduct(Customer customer, int quantity, float overallPrice, Videogame purchasedVideogame) throws MessagingException
    {
        if (purchasedVideogame.getQuantity() == 0)
        {
            return false;
        }
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = customer.getBalance() - overallPrice;
        customer.setBalance(newUserBalance);
        //store the new avaliable quntity of the videogame
        int newQuantity = purchasedVideogame.getQuantity() - quantity;
        //update the avaliable quantity of the videogame
        purchasedVideogame.setQuantity(newQuantity);
        customer.addVideogame(purchasedVideogame);
        //update the customer record in the databse with the new data
        customerService.saveCustomerIntoDB(customer);
        //update the videogame record in the database with the new data
        videogameService.updateVideogame(purchasedVideogame);
        //create an order and a history record of the user balance before and after the payment
        Order order = createOrder(customer,quantity,purchasedVideogame);
        moneyHistoryRecord(order, oldCustomerBalance,newUserBalance);
        sendOrderMail(order);
        return true;
    }
    @Transactional
    Order createOrder(Customer customer, int quantity, Videogame videogame)
    {
        Order order = new Order();
        order.setOrderTransaction(RandomString.make(64));
        order.setCustomer(customer);
        order.setPurchaseDate(LocalDate.now());
        order.setPurchaseTime(LocalTime.now());
        order.setQuantity(quantity);
        order.setVideogame(videogame);
        orderRepository.save(order);
        return order;
    }
    @Transactional
    void moneyHistoryRecord(Order order, float moneyAmountBeforeOrder, float moneyAmountAfterOrder)
    {
        CustomerMoneyHistory customerMoneyHistory = new CustomerMoneyHistory();
        customerMoneyHistory.setOrder(order);
        customerMoneyHistory.setMoneyBeforeOrder(moneyAmountBeforeOrder);
        customerMoneyHistory.setMoneyAfterOrder(moneyAmountAfterOrder);
        customerMoneyHistoryRepository.save(customerMoneyHistory);
    }

    public boolean isBalanceSufficent(float videogamePrice, float customerBalance)
    {
        if (customerBalance < videogamePrice)
        {
            System.out.println("The user balance isn't enough");
            return false;
        }
        return true;
    }
    @Transactional
    void sendOrderMail(Order order) throws MessagingException
    {
        String body = "The game name is " + order.getVideogame().getGameName() + " The amount is " + order.getQuantity()
                + " The date is " + order.getPurchaseDate() + " The time is " + order.getPurchaseTime().format(DateTimeFormatter.ISO_LOCAL_TIME) + " Order Id is "
                + order.getId();
        String subject = "Thanks " + order.getCustomer().getFirstName();
        emailUtil.sendEmail(order.getCustomer().getEmail(), subject,body);
    }
}