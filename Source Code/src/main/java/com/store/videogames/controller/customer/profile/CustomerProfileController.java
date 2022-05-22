package com.store.videogames.controller.customer.profile;

import com.store.videogames.config.security.CustomerDetailsServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerProfileController
{
    @GetMapping("/userProfile")
    public String getUserProfile(@AuthenticationPrincipal CustomerDetailsServiceImpl customerDetailsServiceImpl, Model model)
    {
        model.addAttribute("user", customerDetailsServiceImpl);
        return "/customer/detailsPage/user_profile";
    }
}
