package com.store.videogames.repository.entites;

import com.store.videogames.repository.entites.enums.Platforms;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Videogame
{
    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "game_name")
    @NotNull
    public String gameName;

    @ElementCollection(targetClass = Platforms.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "videogame_platform",
            joinColumns = @JoinColumn(name = "videogame_id"))
    @Column(name = "platform_name")
    @NotNull
    private List<Platforms> videogamePlatforms;
    @Column
    @NotNull
    private int quantity;
    @Column
    @NotNull
    private float price;
    @Column(name = "release_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate releaseDate;
    @Column
    @NotNull
    private String publisher;
    @Column
    @NotNull
    private String developer;
    @Column
    @NotNull
    private String description;


    /*
    Setters and getters area
    */

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public LocalDate getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getDeveloper()
    {
        return developer;
    }

    public void setDeveloper(String developer)
    {
        this.developer = developer;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<Platforms> getVideogamePlatforms()
    {
        return videogamePlatforms;
    }

    public void setVideogamePlatforms(List<Platforms> videogamePlatforms)
    {
        this.videogamePlatforms = videogamePlatforms;
    }

    public Videogame()
    {
    }

    public Videogame(String gameName, String platform, int quantity, float price, LocalDate releaseDate, String publisher, String developer, String description) {
        this.gameName = gameName;
        this.quantity = quantity;
        this.price = price;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.developer = developer;
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "Videogame{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", videogamePlatforms=" + videogamePlatforms +
                ", quantity=" + quantity +
                ", price=" + price +
                ", releaseDate=" + releaseDate +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
