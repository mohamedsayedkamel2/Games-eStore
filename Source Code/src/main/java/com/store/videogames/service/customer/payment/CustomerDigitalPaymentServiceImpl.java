package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerMoneyHistoryRepository;
import com.store.videogames.repository.interfaces.OrderRepository;
import com.store.videogames.service.customer.CustomerService;
import com.store.videogames.service.customer.interfaces.ICustomerPaymentSerivce;
import com.store.videogames.service.videogame.VideogameService;
import com.store.videogames.util.interfaces.EmailUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Transactional(rollbackOn = Exception.class)
public class CustomerDigitalPaymentServiceImpl implements ICustomerPaymentSerivce
{
    @Autowired
    private CustomerMoneyHistoryRepository customerMoneyHistoryRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    VideogameService videogameService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EmailUtil emailUtil;

    @Override
    public boolean buyProduct(Customer customer, int quantity, float overallPrice, Videogame videogame) throws MessagingException
    {
        if (videogame.getQuantity() == 0)
        {
            return false;
        }
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = customer.getBalance() - overallPrice;
        customer.setBalance(newUserBalance);
        //store the new avaliable quntity of the videogame
        int newQuantity = videogame.getQuantity() - quantity;
        //update the avaliable quantity of the videogame
        videogame.setQuantity(newQuantity);
        customer.addVideogame(videogame);
        //update the customer record in the databse with the new data
        customerService.saveCustomerIntoDB(customer);
        //update the videogame record in the database with the new data
        videogameService.updateVideogame(videogame);
        //create an order and a history record of the user balance before and after the payment
        Order order = createOrder(customer,quantity,videogame);
        moneyHistoryRecord(order, oldCustomerBalance,newUserBalance);
        sendOrderMail(order);
        return true;
    }

    @Override
    public Order createOrder(Customer customer, int quantity, Videogame videogame)
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

    @Override
    public void moneyHistoryRecord(Order order, float moneyAmountBeforeOrder, float moneyAmountAfterOrder)
    {
        CustomerMoneyHistory customerMoneyHistory = new CustomerMoneyHistory();
        customerMoneyHistory.setOrder(order);
        customerMoneyHistory.setMoneyBeforeOrder(moneyAmountBeforeOrder);
        customerMoneyHistory.setMoneyAfterOrder(moneyAmountAfterOrder);
        customerMoneyHistoryRepository.save(customerMoneyHistory);
    }

    @Override
    public void sendOrderMail(Order order) throws MessagingException
    {
        String body = "You have bought a digital game";
        String subject = "Thanks " + order.getCustomer().getFirstName();
        emailUtil.sendEmail(order.getCustomer().getEmail(), subject,body);
    }
}
