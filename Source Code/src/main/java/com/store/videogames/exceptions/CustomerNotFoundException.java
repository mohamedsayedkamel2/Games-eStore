package com.store.videogames.exceptions;

public class CustomerNotFoundException extends RuntimeException
{
    private String message;

    public CustomerNotFoundException(String message)
    {
        this.message = message;
    }

    private CustomerNotFoundException(){}

    @Override
    public String getMessage()
    {
        return message;
    }
}
