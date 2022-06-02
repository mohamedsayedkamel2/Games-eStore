package com.store.videogames.api.customer;

import com.store.videogames.service.customer.CustomerService;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController
{
    @Autowired
    private CustomerService customerService;

    @PostMapping("/customer/check_email")
    public String checkDuplicateEmail(@Param("id") int id,@Param("email") String email)
    {
        return customerService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
