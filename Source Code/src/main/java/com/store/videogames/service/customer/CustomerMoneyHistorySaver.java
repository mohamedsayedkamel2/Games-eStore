package com.store.videogames.service.customer;

import com.store.videogames.entites.CustomerMoneyHistory;
import com.store.videogames.entites.Order;
import com.store.videogames.repository.CustomerMoneyHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerMoneyHistorySaver
{
    private final CustomerMoneyHistoryRepository customerMoneyHistoryRepository;

    @Autowired
    CustomerMoneyHistorySaver(CustomerMoneyHistoryRepository customerMoneyHistoryRepository)
    {
        this.customerMoneyHistoryRepository = customerMoneyHistoryRepository;
    }

    @Transactional
    public void presistPaymentRecord(Order order, float moneyAmountBeforeOrder, float moneyAmountAfterOrder)
    {
        CustomerMoneyHistory customerMoneyHistory = createRecord(order, moneyAmountBeforeOrder, moneyAmountAfterOrder);
        customerMoneyHistoryRepository.save(customerMoneyHistory);
    }

    private CustomerMoneyHistory createRecord(Order order, float moneyAmountBeforeOrder, float moneyAmountAfterOrder) {
        com.store.videogames.entites.CustomerMoneyHistory customerMoneyHistory = new com.store.videogames.entites.CustomerMoneyHistory();
        customerMoneyHistory.setOrder(order);
        customerMoneyHistory.setMoneyBeforeOrder(moneyAmountBeforeOrder);
        customerMoneyHistory.setMoneyAfterOrder(moneyAmountAfterOrder);
        return customerMoneyHistory;
    }
}
