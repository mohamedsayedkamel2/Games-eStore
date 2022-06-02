package com.store.videogames.controller;

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
}
