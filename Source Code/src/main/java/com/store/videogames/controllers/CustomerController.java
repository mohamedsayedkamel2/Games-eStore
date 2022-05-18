package com.store.videogames.controllers;

import com.store.videogames.config.CustomerDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController
{
    @GetMapping("/userProfile")
    public String getUserProfile(@AuthenticationPrincipal CustomerDetails customerDetails, Model model)
    {
        model.addAttribute("user",customerDetails);
        return "/customer/detailsPage/user_profile";
    }
}
