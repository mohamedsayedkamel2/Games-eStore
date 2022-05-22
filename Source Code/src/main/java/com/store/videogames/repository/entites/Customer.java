package com.store.videogames.repository.entites;

import com.store.videogames.repository.entites.enums.AuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import javax.persistence.Column;
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
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotNull
    private String email;

    @Column(name = "password")
    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Roles>roles;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "country")
    @NotNull
    private String countryName;

    @Column(name = "city")
    @NotNull
    private String cityName;

    @Column(name = "balance")
    private float balance;

    @Column(name = "street_name")
    @NotNull
    private String streetName;

    @Column(name = "zip_code")
    @NotNull
    private int zipCode;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "registration_time")
    private LocalTime registrationTime;

    @ManyToMany(fetch = FetchType.LAZY)
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

    @Enumerated(value = EnumType.STRING)
    @Column(name = "auth_provider")
    private AuthenticationProvider authenticationProvider;

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

    public float getBalance()
    {
        return balance;
    }

    public void setBalance(float balance)
    {
        this.balance = balance;
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

    public List<Roles> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Roles> roles)
    {
        this.roles = roles;
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

    public boolean getEnabled()
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

    public String getEmailVerificationCode()
    {
        return emailVerificationCode;
    }

    public void setEmailVerificationCode(String emailVerificationCode)
    {
        this.emailVerificationCode = emailVerificationCode;
    }

    public List<Order> getOrdersList()
    {
        return Collections.unmodifiableList(ordersList);
    }

    public AuthenticationProvider getAuthenticationProvider()
    {
        return authenticationProvider;
    }

    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider)
    {
        this.authenticationProvider = authenticationProvider;
    }

    public void addRole(Roles role)
    {
        this.roles.add(role);
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='"  + '\'' +
                ", enabled=" + enabled +
                ", countryName='" + countryName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", userBalance=" + balance +
                ", streetName='" + streetName + '\'' +
                ", zipCode=" + zipCode +
                ", registrationDate=" + registrationDate +
                ", registrationTime=" + registrationTime +
                ", videogameList=" + videogameList +
                ", ordersList=" + getOrdersList() +
                ", resetPasswordToken='" + resetPasswordToken + '\'' +
                ", emailVerificationCode='" + emailVerificationCode + '\'' +
                '}';
    }
}
