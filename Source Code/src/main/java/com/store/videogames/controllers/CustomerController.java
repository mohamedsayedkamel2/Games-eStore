package com.store.videogames.controllers;

import com.store.videogames.config.PasswordEncoder;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.services.VideogameService;
import com.store.videogames.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    EmailUtil emailUtil;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VideogameService videogameService;

    @GetMapping("/newCustomer")
    public String getNewCustomerPage(@ModelAttribute("customer") Customer customer)
    {
        return "newCustomer";
    }

    @PostMapping("/newCustomer")
    public String createNewCustomer(@ModelAttribute("customer") Customer customer, BindingResult bindingResult) throws MessagingException {
        long startTime = System.currentTimeMillis();
        if (bindingResult.hasErrors())
        {
            System.out.println("Errors happend while binding");
            return "index";
        }
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        customer.setPassword(PasswordEncoder.bCryptPasswordEncoder(customer.getPassword()));
        customerRepository.save(customer);
        emailUtil.sendEmail(customer.getEmail(),"Thank you for registering in our website","Hello from Spring Boot " + customer.getUsername());
        System.out.println("customer named: " + customer.getFirstName() + " Had been saved into the DB");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime / 100);
        return "redirect:/customer/allCustomers";
    }

    @GetMapping("/allCustomers")
    String getAllCustomers(Model model)
    {
        List<Customer> listOfCustomers = customerRepository.findAll();
        model.addAttribute("allCustomers", listOfCustomers);
        return "allCustomers";
    }
    @GetMapping("newVideogame")
    String getBuynewVideogamePage()
    {
        return "newVideogame";
    }

    @PostMapping("buyVideogame")
    String customerBuyVideogame(HttpServletRequest request) throws MessagingException {
        String email = request.getParameter("email");
        String videogameName = request.getParameter("videogame");
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer == null)
        {
            System.out.println("Customer not found");
            return "redirect:/";
        }
        Videogame recived_game = videogameService.getVideogameBygameName(videogameName);
        if (recived_game == null)
        {
            System.out.println("Videogame is not found");
            return "redirect:/";
        }
        customer.addVideogame(recived_game);
        customerRepository.save(customer);

        System.out.println("Customer had bought a videogame successfuly");
        return "redirect:/customer/allCustomers";
    }
}
