package com.store.videogames.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class WebsiteNavigationController
{

    @GetMapping("/")
    public String getHomePage()
    {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName() + " " + authentication.getAuthorities() + " " + authentication.getDetails());
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
        {
            return "/login";
        }
        return "index";
    }
}
