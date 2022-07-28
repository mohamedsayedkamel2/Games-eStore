package com.store.videogames.controller.videogame;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.entites.enums.Platforms;
import com.store.videogames.service.videogame.VideogameRetrivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/videogames")
public class VideogameController
{
    @Autowired
    private VideogameRetrivingService videogameRetrivingService;

    @GetMapping
    public String getAllVidoegames(Model model)
    {
        //The displayByPageNumber function will return "/videogames" i will append "/allVideogames" on it
        return displayByPageNumber(null, 1, model) + "/allVideogames";
    }

    @GetMapping("/{pageNumber}")
    public String displayAll(Model model, @PathVariable int pageNumber)
    {
        return displayByPageNumber(null,pageNumber,model) + "/allVideogames";
    }

    String displayByPageNumber(Platforms platform, int currentPage, Model model)
    {
        Page<Videogame> page;
        //If platform is null then this means the function requests all the videogames
        if (platform == null)
        {
            page = videogameRetrivingService.retriveAllVideogames(currentPage);
        }
        //If the platform is not null then this means the function requests a list of videogames of particular platform
        else
        {
            page = videogameRetrivingService.getVideogamesByPlatform(platform, currentPage);
        }
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Videogame> videogames = page.getContent();
        model.addAttribute("videogames",videogames);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "videogames";
    }

    @GetMapping("/ps4")
    public String getAllPs4GamesFirstPage(Model model)
    {
        return getAllPs4Games(model, 1);
    }

    @GetMapping("/ps4/{pageNumber}")
    public String getAllPs4Games(Model model, @PathVariable("pageNumber") int currentPage)
    {
        return displayByPageNumber(Platforms.PS4, currentPage, model) + "/allPs4Games";
    }

    @GetMapping("/ps5")
    public String getAllPs5GamesFirstPage(Model model)
    {
        return getAllPs5Games(model, 1);
    }

    @GetMapping("/ps5/{pageNumber}")
    public String getAllPs5Games(Model model, @PathVariable("pageNumber") int currentPage)
    {
        return displayByPageNumber(Platforms.PS5, currentPage, model) + "/allPs5Games";
    }

    @GetMapping("/pc")
    public String getAllPcGamesFirstPage(Model model)
    {
        return getAllPcGames(model, 1);
    }

    @GetMapping("/pc/{pageNumber}")
    public String getAllPcGames(Model model, @PathVariable("pageNumber") int currentPage)
    {
        return displayByPageNumber(Platforms.PC, currentPage, model) + "/allPcGames";
    }

}
