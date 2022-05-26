package com.store.videogames.controller.customer.login;

import com.store.videogames.util.common.IsAuthenticatedChecker;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerLoginController
{
    @GetMapping("/login")
    public String getLoginPage()
    {
        if (IsAuthenticatedChecker.checkIfAuthenticated() != true)
        {
            return "/customer/login";
        }
        return "index";
    }
}
