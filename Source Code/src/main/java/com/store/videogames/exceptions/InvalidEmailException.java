package com.store.videogames.exceptions;

public class InvalidEmailException extends RuntimeException
{
    private String message;
    private InvalidEmailException(String message)
    {
        this.message = message;
    }
    private InvalidEmailException(){}
    public String getMessage()
    {
        return message;
    }
}
