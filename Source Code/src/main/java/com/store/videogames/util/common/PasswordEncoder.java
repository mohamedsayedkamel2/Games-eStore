package com.store.videogames.util.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder
{
    public static BCryptPasswordEncoder getBcryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
