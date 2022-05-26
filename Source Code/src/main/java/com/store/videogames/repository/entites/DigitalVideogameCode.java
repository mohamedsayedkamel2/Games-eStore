package com.store.videogames.repository.entites;


import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "digital_videogames_codes")
@Table(name = "digital_videogames_codes")
@ToString
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_digitalgame_codes", joinColumns = @JoinColumn (name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "videogame_code"))
    private Customer customer;

    public Long getId() {
        return Id;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public Videogame getVideogame() {
        return videogame;
    }

    public void setVideogame(Videogame videogame) {
        this.videogame = videogame;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
