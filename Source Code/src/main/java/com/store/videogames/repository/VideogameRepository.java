package com.store.videogames.repository;

import com.store.videogames.entites.Videogame;
import com.store.videogames.entites.enums.Platforms;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface VideogameRepository extends JpaRepository<Videogame, Integer>
{
    Videogame getVideogameBygameName(String gameName);
    List<Videogame> getVideogameByreleaseDate(LocalDate date);
    List<Videogame> getVideogameBypublisher(String publisher);
    List<Videogame> getVideogameBydeveloper(String developer);

    @Query("SELECT v FROM Videogame v where v.platform = ?1")
    Page<Videogame>getAllGamesByPlatform(Platforms platform, Pageable pageable);
}
