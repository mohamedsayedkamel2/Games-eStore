package com.store.videogames.exceptions.exception;

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
