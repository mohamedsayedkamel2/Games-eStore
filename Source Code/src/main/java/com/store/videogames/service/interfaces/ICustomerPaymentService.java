package com.store.videogames.service.interfaces;

import com.store.videogames.repository.entites.CustomerMoneyHistory;
import com.store.videogames.repository.entites.Order;

public interface ICustomerPaymentService
{
    CustomerMoneyHistory getMoneyHistoryByOrder(Order order);
}
