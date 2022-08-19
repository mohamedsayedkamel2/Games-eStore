package com.store.videogames.service.customer.account;

import com.store.videogames.exceptions.exception.InsufficientCustomerBalanceException;
import org.springframework.stereotype.Service;

@Service
public class CustomerMoneyService
{
    public void isBalanceSufficentChecker(float videogamePrice, float customerBalance)
    {
        if (customerBalance < videogamePrice)
        {
            throw new InsufficientCustomerBalanceException("Insufficient customer balance. Operation Aborted");
        }
    }
}
