package com.store.videogames.exceptions.exception;

public class InvalidPasswordRestToken extends RuntimeException
{
    private String message;

    public InvalidPasswordRestToken(String message) {
        this.message = message;
    }

    private InvalidPasswordRestToken() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}