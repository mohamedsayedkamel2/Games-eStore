package com.store.videogames.controller.videogame;

import com.store.videogames.config.security.CustomerDetailsImpl;
import com.store.videogames.entites.enums.PaymentMethod;
import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Videogame;
import com.store.videogames.repository.VideogameRepository;
import com.store.videogames.service.payment.impl.PaymentExecutionService;
import com.store.videogames.service.videogame.VideogameRetrivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/videogames")
public class VideogamesPaymentController
{
    private final VideogameRetrivingService videogameRetrivingService;
    private final VideogameRepository videogameRepository;
    private final PaymentExecutionService paymentExecutionService;

    @Autowired
    public VideogamesPaymentController(VideogameRetrivingService videogameRetrivingService,
                                       VideogameRepository videogameRepository,
                                       PaymentExecutionService paymentExecutionService)
    {
        this.videogameRetrivingService = videogameRetrivingService;
        this.videogameRepository = videogameRepository;
        this.paymentExecutionService = paymentExecutionService;
    }


    @GetMapping("/buy/{id}")
    public String buyVideogame(@PathVariable("id") int id, RedirectAttributes redirectAttributes, Model model)
    {
        Videogame videogame = videogameRepository.getById(id);
        if (videogame == null)
        {
            redirectAttributes.addFlashAttribute("message","The game your are looking for is not found");
            return "redirect:/videogames";
        }
        PaymentMethod[] paymentMethodArray = PaymentMethod.values();

        model.addAttribute("videogame",videogame);
        model.addAttribute("paymentMethods", paymentMethodArray);
        return "/videogames/buyGamePage";
    }

    @PostMapping("/buy")
    String buyVideogameProcess(@RequestParam("id") int id, @AuthenticationPrincipal CustomerDetailsImpl customerDetailsImpl, RedirectAttributes redirectAttributes, Model model, PaymentMethod paymentMethod)
    {
        // Get the current logged customer who wants to buy a video game
        Customer customer = customerDetailsImpl.getCustomer();

        // Retrive the video game ID sent using the form
        Videogame videogame = videogameRepository.getById(id);

        // Check if the video game exists in the database or not if not than redirected the user followed by a message
        if (videogame == null)
        {
            redirectAttributes.addFlashAttribute("message","The game isn't found, please go and check other games!");
            return "redirect:/customer/payment/error";
        }

        // Get the game price as the buyGame method will need it later
        paymentExecutionService.buyGame(customer, videogame, paymentMethod);

        // If the operation was successful than redirect the user to his games followed by a message that notifies him about the new game
        redirectAttributes.addFlashAttribute("message","You have successfuly bought a new game, check your email");
        return "redirect:/customer/games";
    }
}