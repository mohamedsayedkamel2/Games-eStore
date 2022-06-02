package com.store.videogames.repository.entites;

import com.store.videogames.repository.entites.enums.Platforms;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
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
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Videogame implements Serializable
{
    private static final long serialVersionUID = 1L;
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

    @Column(name = "digital_avaliable")
    private boolean isDigitallyAvaliable;
}
