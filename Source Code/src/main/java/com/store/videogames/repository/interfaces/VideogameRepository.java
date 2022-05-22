package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface VideogameRepository extends JpaRepository<Videogame, Integer>
{
    /**
     * @param gameName this is the game we are looking for in the database
     * @return a videogame object and returns a NULL Videogame object if not found
     * @since version 1.0
     */
    Videogame getVideogameBygameName(String gameName);
    //This will return a list of videogames released in a particular date
    List<Videogame> getVideogameByreleaseDate(LocalDate date);
    //This will return a list of videogames publisher by a particular publisher
    List<Videogame> getVideogameBypublisher(String publisher);
    //This will return a list of videogames developed by a particular developer
    List<Videogame> getVideogameBydeveloper(String developer);

    @Query(value = "SELECT v FROM Videogame v WHERE v.quantity != 0")
    List<Videogame> getAllVideogames();
}
