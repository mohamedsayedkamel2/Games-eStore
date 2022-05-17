package com.store.videogames.controllers;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.entites.enums.Platforms;
import com.store.videogames.services.VideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/videogame")
public class VideogameController
{
    @Autowired
    VideogameService videogameService;

    @GetMapping("/add")
    String getAddVideogamePage(@ModelAttribute("videogame") Videogame videogame, Model model)
    {
        model.addAttribute("list", Platforms.values());
        return "createVideogame";
    }

    @GetMapping("createVideogame")
    public String getCreateVideogame()
    {
        return "createVideogame";
    }

    @PostMapping("/add")
    public String createNewVideogame(@ModelAttribute("videogame") Videogame videogame, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            System.out.println(bindingResult.getAllErrors());
            return "redirect:/index";
        }
        System.out.println(videogame.getVideogamePlatforms());
        videogameService.storeNewVideogame(videogame);
        return "redirect:/customer/allCustomers";
    }
}
