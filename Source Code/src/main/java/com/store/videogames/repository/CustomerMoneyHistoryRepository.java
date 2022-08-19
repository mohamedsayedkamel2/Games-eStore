package com.store.videogames.repository;

import com.store.videogames.entites.CustomerMoneyHistory;
import com.store.videogames.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerMoneyHistoryRepository extends JpaRepository<CustomerMoneyHistory, Integer>
{
    CustomerMoneyHistory getCustomerMoneyHistoryByOrder(Order order);
}
