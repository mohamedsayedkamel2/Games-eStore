package com.store.videogames.exceptions.exception;

public class CustomerNotFoundException extends RuntimeException
{
    public CustomerNotFoundException(String message)
    {
        super(message);
    }

}
