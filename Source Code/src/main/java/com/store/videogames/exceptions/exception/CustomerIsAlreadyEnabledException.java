package com.store.videogames.exceptions.exception;

public class CustomerIsAlreadyEnabledException extends RuntimeException
{
    private String message;

    public CustomerIsAlreadyEnabledException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}