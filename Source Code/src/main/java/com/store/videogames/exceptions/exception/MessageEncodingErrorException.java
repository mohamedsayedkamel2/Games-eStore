package com.store.videogames.exceptions.exception;

public class MessageEncodingErrorException extends RuntimeException
{
    private String message;

    public MessageEncodingErrorException(String message)
    {
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return this.message;
    }
}