package com.store.videogames.exceptions.exception;

public class UnsuccessfulRegistartion extends RuntimeException
{
    private String message;

    public UnsuccessfulRegistartion(String s)
    {
        this.message = s;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
