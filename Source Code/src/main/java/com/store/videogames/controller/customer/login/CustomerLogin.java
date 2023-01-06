package com.store.videogames.controller.customer.login;

import com.store.videogames.security.AuthenticationChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerLogin
{
    @GetMapping("/login")
    public String getLoginPage()
    {
        if (AuthenticationChecker.checkIfAuthenticated() == false)
        {
            return "/customer/login";
        }
        return "index";
    }
}