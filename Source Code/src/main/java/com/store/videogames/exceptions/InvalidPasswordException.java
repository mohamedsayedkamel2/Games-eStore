package com.store.videogames.exceptions;

public class InvalidPasswordException extends RuntimeException
{
    private String message;
    public InvalidPasswordException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    private InvalidPasswordException(){}
}
