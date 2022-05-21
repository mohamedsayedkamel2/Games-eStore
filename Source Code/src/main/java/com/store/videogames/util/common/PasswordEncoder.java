package com.store.videogames.util.common;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder
{
    @Bean
    public static BCryptPasswordEncoder getBcryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
