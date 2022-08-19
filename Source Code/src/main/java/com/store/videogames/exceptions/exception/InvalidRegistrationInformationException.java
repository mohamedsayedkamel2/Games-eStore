package com.store.videogames.exceptions.exception;

public class InvalidRegistrationInformationException extends RuntimeException
{
    private String message;

    public InvalidRegistrationInformationException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}
