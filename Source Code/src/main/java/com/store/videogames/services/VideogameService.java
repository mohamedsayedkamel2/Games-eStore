package com.store.videogames.services;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.VideogameRepository;
import com.store.videogames.services.interfaces.IVideogameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VideogameService implements IVideogameService
{
    @Autowired
    VideogameRepository videogameRepository;

    @Override
    public void storeNewVideogame(Videogame videogame)
    {
        videogameRepository.save(videogame);
    }

    @Override
    public List<Videogame> retriveAllVideogames()
    {
        return videogameRepository.findAll();
    }

    @Override
    public void updateVideogame(Videogame videogame)
    {
        videogameRepository.save(videogame);
    }

    @Override
    public Videogame getVideogameById(int videogameId)
    {
        return videogameRepository.getById(videogameId);
    }

    @Override
    public Videogame getVideogameBygameName(String gameName)
    {
        return videogameRepository.getVideogameBygameName(gameName);
    }

    @Override
    public List<Videogame> getVideogameByreleaseDate(LocalDate date)
    {
        return videogameRepository.getVideogameByreleaseDate(date);
    }

    @Override
    public List<Videogame> getVideogameBypublisher(String publisher)
    {
        return videogameRepository.getVideogameBypublisher(publisher);
    }

    @Override
    public List<Videogame> getVideogameBydeveloper(String developer)
    {
        return videogameRepository.getVideogameBydeveloper(developer);
    }
}
