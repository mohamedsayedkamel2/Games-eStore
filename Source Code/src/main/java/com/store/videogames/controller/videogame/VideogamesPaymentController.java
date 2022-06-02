package com.store.videogames.controller.videogame;

import com.store.videogames.config.security.CustomerDetailsImpl;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.VideogameRepository;
import com.store.videogames.service.customer.payment.CustomerPaymentExecutionService;
import com.store.videogames.service.videogame.VideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;


@Controller
@RequestMapping("/videogames")
public class VideogamesPaymentController
{
    @Autowired
    private VideogameService videogameService;
    @Autowired
    private VideogameRepository videogameRepository;
    @Autowired
    private CustomerPaymentExecutionService customerPaymentExecutionService;

    @GetMapping("/buy/{id}")
    public String buyVideogame(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model)
    {
        Videogame videogame = videogameRepository.getById(id);
        if (videogame == null)
        {
            redirectAttributes.addFlashAttribute("message","The game your are looking for is unavliable");
            return "videogames";
        }
        model.addAttribute("videogame",videogame);
        return "/videogames/buyGamePage";
    }

    @PostMapping("/buy")
    String buyVideogameProcess(@RequestParam("id") int id,@RequestParam("quantity") int quantity ,@AuthenticationPrincipal CustomerDetailsImpl customerDetailsImpl, @RequestParam(value = "digital", required = false)boolean isDigtial, RedirectAttributes redirectAttributes, Model model) throws MessagingException
    {
        String returnUrlFail = "redirect:/videogames/buy/" + id;
        Customer customer = customerDetailsImpl.getCustomer();
        Videogame videogame = videogameRepository.getById(id);
        if (videogame == null)
        {
            redirectAttributes.addFlashAttribute("message","The game isn't found");
            return returnUrlFail;
        }
        float totalPrice = quantity * videogame.getPrice();
        boolean isPaymentSuccessful = customerPaymentExecutionService.buyGame(customer, quantity, totalPrice, videogame, isDigtial);
        if (isPaymentSuccessful == false)
        {
            redirectAttributes.addFlashAttribute("message","Unsufficent balance");
            return returnUrlFail;
        }
        redirectAttributes.addFlashAttribute("message","You have successfuly bought a new game, check your email");
        return "redirect:/customer/games";
    }
}