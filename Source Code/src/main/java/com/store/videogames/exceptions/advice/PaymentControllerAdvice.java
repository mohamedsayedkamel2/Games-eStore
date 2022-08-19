package com.store.videogames.exceptions.advice;

import com.store.videogames.exceptions.exception.InsufficientCustomerBalanceException;
import com.store.videogames.exceptions.exception.UnvalidVideogameQuantityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PaymentControllerAdvice
{
    @ExceptionHandler(value = InsufficientCustomerBalanceException.class)
    public String insufficientCustomerBalanceHandler()
    {
        return "redirect:/customer/payment/error";
    }

    @ExceptionHandler(value = UnvalidVideogameQuantityException.class)
    public String getError()
    {
        return "Hello";
    }
}