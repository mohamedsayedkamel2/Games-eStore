package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMoneyHistoryRepository extends JpaRepository<CustomerMoneyHistory, Integer>
{
    CustomerMoneyHistory getCustomerMoneyHistoryByOrder(Order order);
}
