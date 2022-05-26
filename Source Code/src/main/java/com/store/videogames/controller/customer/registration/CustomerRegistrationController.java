package com.store.videogames.controller.customer.registration;

import com.store.videogames.exceptions.exception.InvalidEmailException;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.service.customer.account.CustomerEmailService;
import com.store.videogames.service.customer.CustomerService;
import com.store.videogames.service.customer.account.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class CustomerRegistrationController 
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerEmailService customerEmailService;


    @GetMapping("/customer/register")
    public String getNewCustomerPage(@ModelAttribute("customer") Customer customer)
    {
        return "/customer/register";
    }

    @PostMapping("/customer/register")
    @Transactional
    public String createNewCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException
    {
        if (bindingResult.hasErrors())
        {
            throw new InvalidEmailException("Error");
        }
        if (customerRegistrationService.registerCustomer(customer, request) == true)
        {
            System.out.println("Successfuly registerting");
            return "/customer/login";
        }
        System.out.println("Error Happend while registerting");
        return "/";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code)
    {
        if (customerEmailService.verify(code))
        {
            return "verify_success";
        }
        else
        {
            return "verify_fail";
        }
    }
}
