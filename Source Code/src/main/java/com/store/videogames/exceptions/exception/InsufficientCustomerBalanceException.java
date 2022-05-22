package com.store.videogames.exceptions.exception;

public class InsufficientCustomerBalanceException extends RuntimeException
{
    private String message;

    private InsufficientCustomerBalanceException(){}

    public InsufficientCustomerBalanceException(String message)
    {
        this.message = message;
    }
    @Override
    public String getMessage()
    {
        return message;
    }
}
