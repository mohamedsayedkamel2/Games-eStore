package com.store.videogames.entites;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DigitalVideogameCode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @Column(name = "game_code")
    String gameCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Videogame videogame;

    @Override
    public String toString()
    {
        return "DigitalVideogameCode{" +
                "Id=" + Id +
                ", gameCode='" + gameCode + '\'' +
                ", videogame=" + videogame +
                '}';
    }
}
