package com.store.videogames.controllers;

import com.store.videogames.common.IsAuthenticatedCheckerClass;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.services.CustomerEmailService;
import com.store.videogames.services.CustomerService;
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
@Transactional
public class CustomerLoginController
{
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerEmailService customerEmailService;

    @GetMapping("/login")
    public String getLoginPage()
    {
        if (IsAuthenticatedCheckerClass.checkIfAuthenticated() != true)
        {
            return "/customer/login";
        }
        return "index";
    }

    @GetMapping("/customer/register")
    public String getNewCustomerPage(@ModelAttribute("customer") Customer customer)
    {
        return "/customer/register";
    }

    @PostMapping("/customer/register")
    public String createNewCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException
    {
        if (bindingResult.hasErrors())
        {
            System.out.println("Errors happend while binding");
            return "redirect:/index";
        }
        if (customerService.registerCustomer(customer, request) == true)
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

    @GetMapping("newVideogame")
    String getBuynewVideogamePage()
    {
        return "/videogame/newVideogame";
    }

}
