package com.store.videogames.repository;

import com.store.videogames.entites.DigitalVideogameCode;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DigitalVideogameCodeRepositoryTest
{
    @Autowired
    private DigitalVideogameCodeRepository digitalVideogameCodeRepository;

    @Test
    public void getAllCodes()
    {
        digitalVideogameCodeRepository.findAll();
    }

    @Test
    public void createCode()
    {
        DigitalVideogameCode digitalVideogameCode = new DigitalVideogameCode();
        digitalVideogameCode.setId(10000L);
        digitalVideogameCode.setGameCode(RandomString.make(15));
        digitalVideogameCode.setVideogame(null);
        digitalVideogameCodeRepository.save(digitalVideogameCode);
    }

    @Test
    public void deleteCode()
    {
        digitalVideogameCodeRepository.deleteById(1L);
    }
}
