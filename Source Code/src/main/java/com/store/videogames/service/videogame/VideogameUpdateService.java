package com.store.videogames.service.videogame;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.VideogameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class VideogameUpdateService
{
    @Autowired
    private VideogameRepository videogameRepository;

    @CachePut("Videogame")
    public void storeNewVideogame(Videogame videogame)
    {
        videogameRepository.save(videogame);
    }

}
