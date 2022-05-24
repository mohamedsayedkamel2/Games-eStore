package com.store.videogames.controller;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.service.videogame.VideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class WebsiteNavigationController
{
    @Autowired
    VideogameService videogameService;

    @GetMapping("/")
    public String getHomePage(Model model)
    {
        return displayByPageNumber(model,1);
    }
    @GetMapping("/{pageNumber}")
    String displayByPageNumber(Model model, @PathVariable("pageNumber") int currentPage)
    {
        Page<Videogame> page = videogameService.retriveAllVideogames(currentPage);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Videogame> videogames = page.getContent();
        model.addAttribute("videogames",videogames);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "index";
    }
}
