package com.store.videogames.service.interfaces;

import com.store.videogames.repository.entites.Videogame;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface IVideogameService
{
    //This will save the videogame into the database
    void storeNewVideogame(@Valid Videogame videogame);

    //This will get all videogames stored in the database
    List<Videogame> retriveAllVideogames();

    //This will update the videogame stored in the database
    void updateVideogame(Videogame videogame);

    //This will get the videogame stored in the database using the id sent by the user
    Videogame getVideogameById(int videogameId);

    //This will get the videogame stored in the database using the videogame name and it will return a NULL videogame Object
    Videogame getVideogameBygameName(String gameName);

    //This will return a list of videogames released in a particular date
    List<Videogame> getVideogameByreleaseDate(LocalDate date);

    //This will return a list of videogames publisher by a particular publisher
    List<Videogame> getVideogameBypublisher(String publisher);

    //This will return a list of videogames developed by a particular developer
    List<Videogame> getVideogameBydeveloper(String developer);
}
