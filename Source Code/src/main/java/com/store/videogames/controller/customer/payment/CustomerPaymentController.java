package com.store.videogames.controller.customer.payment;

import com.store.videogames.config.security.CustomerDetailsImpl;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.OrderRepository;
import com.store.videogames.service.customer.CustomerPaymentServiceImpl;
import com.store.videogames.service.customer.CustomerServiceImpl;
import com.store.videogames.service.videogame.VideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;

@Controller
public class CustomerPaymentController
{
    @Autowired
    VideogameService videogameService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CustomerServiceImpl customerServiceImpl;
    @Autowired
    CustomerPaymentServiceImpl customerPaymentService;

    @GetMapping("/videogame/{id}")
    String getGamePaymentPage(@PathVariable("id") int id, Model model)
    {
        Videogame videogame = videogameService.getVideogameById(id);
        if (videogame == null)
        {
            System.out.println("Couldn't find the videogame");
            return "/login";
        }
        model.addAttribute("videogame",videogame);
        return "/customer/buyVideogame";
    }

    @PostMapping("/videogame/buy")
    @ResponseBody
    String buyVideogameProcess(@AuthenticationPrincipal CustomerDetailsImpl customerDetailsImpl, @ModelAttribute("videogame") Videogame videogame) throws MessagingException
    {
        Customer customer = customerDetailsImpl.getCustomer();
        Videogame videogame1 = videogameService.getVideogameById(videogame.getId());
        if (videogame1 == null)
        {
            return "Videogame object is not found";
        }
        float videogamePrice = videogame.getQuantity() * videogame1.getPrice();
        boolean isPaymentSuccessful = false;
        if (customerPaymentService.isBalanceSufficent(videogamePrice,customer.getBalance()))
        {
            isPaymentSuccessful = customerPaymentService.buyProduct(customer, videogame.getQuantity(), videogamePrice, videogame1);
        }
        if (isPaymentSuccessful == false)
        {
            return "failure";
        }
        return "Sucess";
    }
}