package com.store.videogames.repository;

import com.store.videogames.entites.CustomerMoneyHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerMoneyHistoryRepositoryTest
{
    @Autowired
    private CustomerMoneyHistoryRepository customerMoneyHistoryRepository;

    @Test
    public void getAllMoneyHistory()
    {
        customerMoneyHistoryRepository.findAll();
    }

    @Test
    public void createMoneyHistory()
    {
        CustomerMoneyHistory customerMoneyHistory = new CustomerMoneyHistory();
        customerMoneyHistory.setOrder(null);
        customerMoneyHistory.setMoneyAfterOrder(4000);
        customerMoneyHistory.setMoneyBeforeOrder(5000);
        customerMoneyHistoryRepository.save(customerMoneyHistory);
    }

    @Test
    public void remove()
    {
        customerMoneyHistoryRepository.deleteById(5);
    }
}
