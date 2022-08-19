package com.store.videogames.admin.controller.videogame;

import com.store.videogames.entites.Videogame;
import com.store.videogames.entites.enums.Platforms;
import com.store.videogames.repository.VideogameRepository;
import com.store.videogames.service.videogame.VideogameRetrivingService;
import com.store.videogames.service.videogame.VideogameUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/games")
public class VideogameManagementController
{
    @Autowired
    private VideogameRetrivingService videogameRetrivingService;
    @Autowired
    private VideogameRepository videogameRepository;
    @Autowired
    private VideogameUpdateService videogameUpdateService;

    //The create section

    @GetMapping("/add")
    public String getCreateGame(Model model)
    {
        Videogame videogame = new Videogame();
        List<Platforms> platformsList = List.of(Platforms.values());
        model.addAttribute("videogame",videogame);
        model.addAttribute("platforms",platformsList);
        return "/admin/games/createNewGame";
    }

    @PostMapping("/add")
    public String createNewGame(Videogame videogame, RedirectAttributes redirectAttributes)
    {
        videogame.setDigitallyAvaliable(true);
        videogameUpdateService.storeNewVideogame(videogame);
        redirectAttributes.addFlashAttribute("videogame", "New game had been created successfuly!");
        return "redirect:/admin/games";
    }

    //The Get Section
    @GetMapping
    public String getAllGames(Model model)
    {
        return getAllGamesPage(model,1);
    }

    @GetMapping("/{page}")
    public String getAllGamesPage(Model model, @PathVariable("page")int currentPage)
    {
        Pageable pageable = PageRequest.of(currentPage - 1, 3);
        Page<Videogame>page = videogameRepository.findAll(pageable);
        long totalItems = page.getTotalElements();
        long totalPages = page.getTotalPages();
        List<Videogame> videogames = page.getContent();
        model.addAttribute("videogames",videogames);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("currentPage",currentPage);
        return "/admin/games/viewAllGames";
    }

    //The update section
    @GetMapping("/edit/{id}")
    public String getEditPage(@PathVariable int id,Model model)
    {
        Videogame videogame = videogameRepository.getById(id);
        model.addAttribute("videogame",videogame);
        return "/admin/games/editGame";
    }

    @PostMapping("/edit")
    public String editGame(Videogame videogame, RedirectAttributes redirectAttributes)
    {
        Videogame existingVideogame = videogameRepository.getById(videogame.getId());
        if (!videogame.getGameName().isEmpty())
        {
            existingVideogame.setGameName(videogame.getGameName());
        }
        if (videogame.getPrice() != 0.0)
        {
            existingVideogame.setPrice(videogame.getPrice());
        }
        videogameRepository.save(existingVideogame);
        return "redirect:/admin/games";
    }


    //The delete section
    @GetMapping("/delete/{id}")
    public String deleteGame(@PathVariable int id, RedirectAttributes redirectAttributeso)
    {
        Videogame videogame = videogameRepository.getById(id);
        if (videogame == null)
        {
            redirectAttributeso.addFlashAttribute("message","The game wasn't found");
        }
        videogameRepository.delete(videogame);
        redirectAttributeso.addFlashAttribute("message", "The game ID: " + videogame.getId() + " Had been removed");
        return "redirect:/admin/games/";
    }
}
