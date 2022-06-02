package com.store.videogames.admin.controller.customer;

import com.store.videogames.exceptions.exception.CustomerNotFoundException;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.interfaces.RolesRepository;
import com.store.videogames.service.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class CustomerManagementController
{
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String getAllCustomers(Model model)
    {
        List<Customer> customerList = customerService.getAll();
        model.addAttribute("listCustomers", customerList);
        return "/admin/customers/getAllCustomers";
    }

    @GetMapping("/add")
    public String addNewCustomerPage(Model model)
    {
        List<Roles> rolesList = rolesRepository.findAll();
        Customer customer = new Customer();
        customer.setEnabled(true);
        model.addAttribute("customer",customer);
        model.addAttribute("roles",rolesList);
        return "/admin/customers/newCustomerPage";
    }

    @PostMapping("/add")
    public String createNewCustomer(Customer customer, RedirectAttributes redirectAttributes)
    {
        customer.setEmail(customer.getEmail());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRegistrationDate(LocalDate.now());
        customer.setRegistrationTime(LocalTime.now());
        customerService.saveCustomerIntoDB(customer);
        redirectAttributes.addFlashAttribute("message", "The user had been saved successfuly!");
        return "redirect:/admin/customers";
    }

    @GetMapping("/update/{id}")
    public String getEditUserPage(@PathVariable("id")int id, RedirectAttributes redirectAttributes, Model model)
    {
        try
        {
            Customer customer = customerService.getCustomerById(id);
            List<Roles> rolesList = rolesRepository.findAll();
            model.addAttribute("customer",customer);
            model.addAttribute("pageTitle","Edit customer ID: " + customer.getId());
            model.addAttribute("roles",rolesList);
        }
        catch (CustomerNotFoundException exception)
        {
            redirectAttributes.addFlashAttribute("message",exception.getMessage());
            return "redirect:/admin/customers";
        }
        return "/admin/customers/editCustomer";
    }


    @PostMapping("/update")
    public String updateCustomer(Customer customer, RedirectAttributes redirectAttributes)
    {
        Customer existingCustomer = customerService.getCustomerById(customer.getId());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setEnabled(customer.isEnabled());
        if (!customer.getPassword().isEmpty())
        {
            existingCustomer.setPassword(customer.getPassword());
        }
        if (!customer.getEmail().isEmpty())
        {
            existingCustomer.setEmail(customer.getEmail());
        }
        if (!customer.getRoles().isEmpty())
        {
            existingCustomer.setRoles(customer.getRoles());
        }
        existingCustomer.setBalance(existingCustomer.getBalance());
        customerService.updateCustomer(existingCustomer);
        redirectAttributes.addFlashAttribute("message", "The user ID: " + customer.getId() + " Had been updated successfuly!");
        return "redirect:/admin/customers";
    }


    @GetMapping("/changeEnable/{id}/enabled/{status}")
    public String changeEnableStatus(@PathVariable("id") int id, @PathVariable("status") boolean status, RedirectAttributes redirectAttributes)
    {
        customerService.updateCustomerEnabled(id,status);
        String isEnabledStatus = status ? "Enabled" : "Disabled";
        String message = "User id: " + id + " has been " + isEnabledStatus;
        redirectAttributes.addFlashAttribute("message",message);
        return "redirect:/admin/customers";
    }
}