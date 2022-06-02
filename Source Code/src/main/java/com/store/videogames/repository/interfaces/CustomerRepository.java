package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.entites.Videogame;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{

    /**
     * This function is special because the spring seucrity uses it for user login form
     */
    @Query("SELECT u FROM Customer u WHERE u.email = :email")
    Customer getConfigUsername(@Param("email") String email);

    //These functions gets a Customer Object
    Customer getCustomerByEmail(String email);
    Customer getCustomerByUsername(String username);
    Customer getCustomerByResetPasswordToken(String token);

    @Query("SELECT u FROM Customer u WHERE u.emailVerificationCode = ?1")
    Customer getCustomerByEmailVerificationCode(String emailVerificationCode);

    //These functions get a List of Customer Objects
    List<Customer> getCustomerByCountryName(String countryName);
    List<Customer> getCustomerByCityName(String cityName);
    List<Customer> getCustomerByStreetName(String streetName);
    List<Customer> getCustomerByZipCode(int zipCode);
    List<Customer> getCustomerByRegistrationDate(LocalDate registrationDate);
//    List<Customer> getCustomerByRoles(List<Roles> roles);
    List<Customer> getCustomerByEnabled(Boolean isEnabled);
    List<Customer> getCustomerByRegistrationTime(LocalTime registrationTime);

    @Query("UPDATE Customer u SET u.enabled= ?2 WHERE u.id = ?1")
    @Modifying
    public void updateEnabledStatus(int id, boolean enabled);

    @Query("UPDATE Customer c SET c=?1 WHERE c.id=?2")
    @Modifying
    public void updateCustomer(Customer customer, int id);
}
