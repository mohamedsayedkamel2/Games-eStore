package com.store.videogames.controller.customer.registration;

import com.store.videogames.exceptions.exception.InvalidRegistrationInformationException;
import com.store.videogames.entites.Customer;
import com.store.videogames.service.customer.CustomerInformationRetriverService;
import com.store.videogames.service.customer.account.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
public class CustomerRegistrationController 
{
    @Autowired
    private CustomerInformationRetriverService customerInformationRetriverService;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @GetMapping("/customer/register")
    public String getNewCustomerPage(@ModelAttribute("customer") Customer customer)
    {
        return "/customer/register";
    }

    @PostMapping("/customer/register")
    @Transactional
    public String createNewCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidRegistrationInformationException("Invalid Registration information, please check you input!");
        }

        customerRegistrationService.registerCustomer(customer, request);

        return "index";
    }
}