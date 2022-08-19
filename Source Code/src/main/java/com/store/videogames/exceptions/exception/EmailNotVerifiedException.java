package com.store.videogames.exceptions.exception;

public class EmailNotVerifiedException extends RuntimeException
{
    private String message;

    public EmailNotVerifiedException(String message)
    {
        this.message = message;
    }

    private EmailNotVerifiedException(){}

    @Override
    public String getMessage()
    {
        return message;
    }
}