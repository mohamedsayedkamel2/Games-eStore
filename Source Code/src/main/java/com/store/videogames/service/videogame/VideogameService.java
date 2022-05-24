package com.store.videogames.service.videogame;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VideogameService
{
    @Autowired
    VideogameRepository videogameRepository;

    public void storeNewVideogame(Videogame videogame)
    {
        videogameRepository.save(videogame);
    }

    public Page<Videogame> retriveAllVideogames(int currentPage)
    {
        Pageable pageable = PageRequest.of(currentPage, 3);
        return videogameRepository.findAll(pageable);
    }

    public void updateVideogame(Videogame videogame)
    {
        videogameRepository.save(videogame);
    }

    public Videogame getVideogameById(int videogameId)
    {
        return videogameRepository.getById(videogameId);
    }

    public Videogame getVideogameBygameName(String gameName)
    {
        return videogameRepository.getVideogameBygameName(gameName);
    }

    public List<Videogame> getVideogameByreleaseDate(LocalDate date)
    {
        return videogameRepository.getVideogameByreleaseDate(date);
    }

    public List<Videogame> getVideogameBypublisher(String publisher)
    {
        return videogameRepository.getVideogameBypublisher(publisher);
    }

    public List<Videogame> getVideogameBydeveloper(String developer)
    {
        return videogameRepository.getVideogameBydeveloper(developer);
    }

    public List<Videogame> searchforVideogame(String keyword)
    {
        return videogameRepository.videogamesSearch(keyword);
    }
}
