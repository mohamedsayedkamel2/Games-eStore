package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMoneyHistoryRepository extends JpaRepository<CustomerMoneyHistory, Integer>
{
    CustomerMoneyHistory getCustomerMoneyHistoryByOrder(Order order);
}
