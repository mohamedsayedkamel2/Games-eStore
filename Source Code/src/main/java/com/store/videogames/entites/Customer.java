package com.store.videogames.entites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(name = "Customer.roles", attributeNodes = @NamedAttributeNode("roles"))
@Getter
@Setter
public class Customer implements Serializable
{
    private static final long serialVersionUID = 6819399298611410234L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotNull
    @Email
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "newuser_roles", joinColumns = @JoinColumn(name = "newuser_id"),
            inverseJoinColumns = @JoinColumn(name = "newrole_id"))
    private List<Roles>roles;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "country")
    private String countryName;

    @Column(name = "city")
    private String cityName;

    @Column(name = "balance")
    private float balance;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "registration_time")
    private LocalTime registrationTime;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "customer_games",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_videogame"))
    private List<Videogame> videogameList;

    @OneToMany(mappedBy = "customer")
    private List<Order> ordersList;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "email_verification_code", length = 60)
    private String emailVerificationCode;

    public void addVideogame(Videogame videogame)
    {
        this.videogameList.add(videogame);
    }

    public void addRole(Roles role)
    {
        this.roles.add(role);
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id;
    }
}
