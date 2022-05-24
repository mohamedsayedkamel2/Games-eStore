package com.store.videogames.repository.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "videogame_code")
@Table(name = "videogame_code")
public class DigitalVideogameCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @Column(name = "game_code")
    String gameCode;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videogame_id")
    Videogame videogame;
    @Column(name = "is_sold")
    boolean isSold;
}
