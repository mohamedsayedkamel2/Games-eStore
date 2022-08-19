package com.store.videogames.api.customer;

import com.store.videogames.service.customer.CustomerInformationRetriverService;
import com.store.videogames.service.customer.account.CustomerEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController
{
    @Autowired
    private CustomerEmailService customerEmailService;

    @PostMapping("/customer/check_email")
    public String checkDuplicateEmail(@RequestParam("id") int id, @RequestParam("email") String email)
    {
        return customerEmailService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
