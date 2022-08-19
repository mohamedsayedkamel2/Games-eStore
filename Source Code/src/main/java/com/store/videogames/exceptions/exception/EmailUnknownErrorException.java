package com.store.videogames.exceptions.exception;

public class EmailUnknownErrorException extends RuntimeException
{
    private String message;

    public EmailUnknownErrorException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}