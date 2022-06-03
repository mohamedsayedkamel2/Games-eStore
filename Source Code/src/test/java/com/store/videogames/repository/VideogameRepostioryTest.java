package com.store.videogames.repository;

import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.entites.enums.Platforms;
import com.store.videogames.repository.interfaces.VideogameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class VideogameRepostioryTest
{
    @Autowired
    private VideogameRepository videogameRepository;
    
    @Test
    public void getAllGames()
    {
        videogameRepository.findAll();
    }

    @Test
    public void createNewGame()
    {
        Videogame videogame = new Videogame();
        videogame.setId(100);
        videogame.setPlatform(Platforms.PC);
        videogame.setDeveloper("Random");
        videogame.setDigitallyAvaliable(true);
        videogame.setPrice(100);
        videogame.setPublisher("WB Games");
        videogame.setReleaseDate(LocalDate.now());
        videogameRepository.save(videogame);
    }

    @Test
    public void removeGame()
    {
        videogameRepository.deleteById(4);
    }
}