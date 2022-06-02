package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.DigitalVideogameCode;
import com.store.videogames.repository.entites.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DigitalVideogameCodeRepository extends JpaRepository<DigitalVideogameCode, Long>
{
    @Query("SELECT d FROM DigitalVideogameCode d WHERE d.videogame= ?1")
    List<DigitalVideogameCode> getCodes(Videogame videogame);
}
