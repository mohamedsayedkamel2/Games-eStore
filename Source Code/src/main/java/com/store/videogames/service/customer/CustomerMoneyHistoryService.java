package com.store.videogames.service.customer;

import com.store.videogames.entites.CustomerMoneyHistory;
import com.store.videogames.entites.Order;
import com.store.videogames.repository.CustomerMoneyHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerMoneyHistoryService
{
    private final CustomerMoneyHistoryRepository customerMoneyHistoryRepository;

    @Autowired
    CustomerMoneyHistoryService(CustomerMoneyHistoryRepository customerMoneyHistoryRepository)
    {
        this.customerMoneyHistoryRepository = customerMoneyHistoryRepository;
    }

    @Transactional
    public void createRecord(Order order, float moneyAmountBeforeOrder, float moneyAmountAfterOrder)
    {
        CustomerMoneyHistory customerMoneyHistory = new CustomerMoneyHistory();
        customerMoneyHistory.setOrder(order);
        customerMoneyHistory.setMoneyBeforeOrder(moneyAmountBeforeOrder);
        customerMoneyHistory.setMoneyAfterOrder(moneyAmountAfterOrder);
        customerMoneyHistoryRepository.save(customerMoneyHistory);
    }
}
