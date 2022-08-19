package com.store.videogames.exceptions.exception;

public class UnvalidVideogameQuantityException extends RuntimeException
{
    private String message;

    public UnvalidVideogameQuantityException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}