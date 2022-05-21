package com.store.videogames.repository.entites;

import com.store.videogames.repository.entites.enums.Platforms;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Videogame
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "game_name")
    @NotNull
    public String gameName;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "platforms_name")
    @Column(name = "platform")
    Platforms platform;

    @Column
    float price;

    @Column
    int quantity;

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

    //private List<CustomerMoneyHistory> customerMoneyHistoryList;

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

    public Platforms getPlatform()
    {
        return platform;
    }

    public void setPlatform(Platforms platform)
    {
        this.platform = platform;
    }

    public float getPrice()
    {
        return price;
    }

    public void setPrice(float price)
    {
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
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

    @Override
    public String toString()
    {
        return "Videogame{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", platform=" + platform +
                ", price=" + price +
                ", quantity=" + quantity +
                ", releaseDate=" + releaseDate +
                ", publisher='" + publisher + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
