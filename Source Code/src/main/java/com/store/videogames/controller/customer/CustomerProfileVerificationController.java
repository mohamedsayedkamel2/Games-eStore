package com.store.videogames.controller.customer;

import com.store.videogames.service.customer.account.CustomerEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class CustomerProfileVerificationController
{
    @Autowired
    private CustomerEmailService customerEmailService;

    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code)
    {
        customerEmailService.verify(code);
        return "redirect:/verify_success";
    }

    @GetMapping("/verify_success")
    public String sendMailVerificationPage()
    {
        return "verify_success";
    }
}
