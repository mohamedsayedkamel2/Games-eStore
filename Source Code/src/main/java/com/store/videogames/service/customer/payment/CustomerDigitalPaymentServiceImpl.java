package com.store.videogames.service.customer.payment;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.DigitalVideogameCode;
import com.store.videogames.repository.entites.Order;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerMoneyHistoryRepository;
import com.store.videogames.repository.interfaces.DigitalVideogameCodeRepository;
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
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class CustomerDigitalPaymentServiceImpl implements ICustomerPaymentSerivce
{
    @Autowired
    private DigitalVideogameCodeRepository digitalVideogameCodeRepository;
    @Autowired
    private CustomerMoneyHistoryRepository customerMoneyHistoryRepository;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VideogameService videogameService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public boolean buyProduct(Customer customer, float overallPrice, Videogame videogame) throws MessagingException
    {
        //store old customer balance
        float oldCustomerBalance = customer.getBalance();
        //update the customer balance after buying the videogame
        float newUserBalance = customer.getBalance() - overallPrice;
        customer.setBalance(newUserBalance);
        customer.addVideogame(videogame);
        //update the customer record in the databse with the new data
        customerService.saveCustomerIntoDB(customer);
        //update the videogame record in the database with the new data
        videogameService.storeNewVideogame(videogame);
        List<DigitalVideogameCode> codesList = digitalVideogameCodeRepository.getCodes(videogame);
        DigitalVideogameCode code;
        try
        {
            code = codesList.get(0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
        //create an order and a history record of the user balance before and after the payment
        Order order = createOrder(customer,videogame, code);
        digitalVideogameCodeRepository.delete(code);
        moneyHistoryRecord(order, oldCustomerBalance,newUserBalance);
        sendOrderMail(order);
        return true;
    }

    @Override
    public Order createOrder(Customer customer, Videogame videogame, DigitalVideogameCode digitalVideogameCode)
    {
        Order order = new Order();
        order.setOrderTransaction(RandomString.make(64));
        order.setCustomer(customer);
        order.setPurchaseDate(LocalDate.now());
        order.setPurchaseTime(LocalTime.now());
        order.setVideogame(videogame);
        order.setDigitalVideogameCode(digitalVideogameCode.getGameCode());
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
        String subject =  "Thanks for buying " + order.getVideogame().getGameName();
        String body = "The game code is: " + order.getDigitalVideogameCode() + "     You have bought the game at: " + order.getPurchaseDate()
                +" The transaction ID is: " + order.getOrderTransaction();
        emailUtil.sendEmail(order.getCustomer().getEmail(), subject,body);
    }
}
