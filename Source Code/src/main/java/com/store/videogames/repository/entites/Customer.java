package com.store.videogames.repository.entites;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Integer id;

    @NotNull
    @Column(name = "username")
    String username;

    @Column(name = "first_name")
    @NotNull
    String firstName;

    @NotNull
    @Column(name = "last_name")
    String lastName;

    @Email
    @Column(name = "email")
    @NotNull
    String email;

    @Column(name = "password")
    @NotNull
    String password;

    @Column(name = "role")
    @NotNull
    String role;

    @Column(name = "enabled")
    boolean enabled;

    @Column(name = "country")
    @NotNull
    String countryName;

    @Column(name = "city")
    @NotNull
    String cityName;

    @Column(name = "street_name")
    @NotNull
    String streetName;

    @Column(name = "zip_code")
    @NotNull
    int zipCode;

    @Column(name = "registration_date")
    LocalDate registrationDate;

    @Column(name = "registration_time")
    LocalTime registrationTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "customer_games",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_videogame"))
    private List<Videogame> videogameList;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    /*Setters and getters area*/
    public List<Videogame> getVideogameList()
    {
        return videogameList;
    }

    public Integer getId()
    {
        return id;
    }
    public void addVideogame(Videogame videogame)
    {
        this.videogameList.add(videogame);
    }
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public LocalTime getRegistrationTime()
    {
        return registrationTime;
    }

    public void setRegistrationTime(LocalTime registrationTime)
    {
        this.registrationTime = registrationTime;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setResetPasswordToken(String resetPasswordToken)
    {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getResetPasswordToken()
    {
        return resetPasswordToken;
    }
}
