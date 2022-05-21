package com.store.videogames.controller.login;

import com.store.videogames.util.common.IsAuthenticatedCheckerClass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerLoginController
{
    @GetMapping("/login")
    public String getLoginPage()
    {
        if (IsAuthenticatedCheckerClass.checkIfAuthenticated() != true)
        {
            return "/customer/login";
        }
        return "index";
    }
}
