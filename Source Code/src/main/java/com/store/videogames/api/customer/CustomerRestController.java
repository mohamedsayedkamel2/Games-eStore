package com.store.videogames.api.customer;

import com.store.videogames.service.customer.account.CustomerEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController
{
    private final CustomerEmailService customerEmailService;

    @Autowired
    public CustomerRestController(CustomerEmailService customerEmailService)
    {
        this.customerEmailService = customerEmailService;
    }

    @PostMapping("/customer/check_email")
    public String checkDuplicateEmail(@RequestParam("id") int id, @RequestParam("email") String email)
    {
        return customerEmailService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
