package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>
{
    //This functions gets a Customer Object by it's email
    @Query("SELECT u FROM Customer u WHERE u.email = :email")
    Customer getConfigUsername(@Param("email") String email);

    Customer getCustomerByEmail(String email);
    Customer getCustomerByUsername(String username);
    List<Customer> getCustomerByCountryName(String countryName);
    List<Customer> getCustomerByCityName(String cityName);
    List<Customer> getCustomerByStreetName(String streetName);
    List<Customer> getCustomerByZipCode(int zipCode);
    List<Customer> getCustomerByRegistrationDate(LocalDate registrationDate);
    List<Customer> getCustomerByRole(String role);
    List<Customer> getCustomerByEnabled(Boolean isEnabled);
    List<Customer> getCustomerByRegistrationTime(LocalTime registrationTime);
}
