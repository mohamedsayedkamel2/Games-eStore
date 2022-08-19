package com.store.videogames.exceptions.exception;

public class InvalidEmailException extends RuntimeException
{
    private String message;
    public InvalidEmailException(String message)
    {
        this.message = message;
    }
    private InvalidEmailException(){}

    @Override
    public String getMessage()
    {
        return message;
    }
}
