package com.store.videogames.controller;

import com.store.videogames.config.security.CustomerDetailsImpl;
import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/customer")
@Controller
public class CustomerProfileController
{
    @GetMapping("/games")
    public String getCustomerGames(@AuthenticationPrincipal CustomerDetailsImpl customerDetailsImpl, Model model, RedirectAttributes redirectAttributes)
    {
        String customerGamesUrl = "/customer/allCustomerGames";
        Customer customer = customerDetailsImpl.getCustomer();
        List<Order> customerOrders = customer.getOrdersList();
        if (customerOrders.size() == 0 || customerOrders == null)
        {
            redirectAttributes.addFlashAttribute("message","You don't have any orders. Go and buy some games!");
            return customerGamesUrl;
        }
        model.addAttribute("ordersList", customerOrders);
        return customerGamesUrl;
    }
}
