package com.store.videogames.util.common;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * @version 2.0
 * @implNote in the previous version I made the BCryptEconder method a static and this is bad for testing so I made the class a component class
 */
@Component
public class PasswordEncoder
{
    @Bean
    public BCryptPasswordEncoder getBcryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
