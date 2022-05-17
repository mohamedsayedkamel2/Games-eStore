package com.store.videogames.services.interfaces;

import com.store.videogames.repository.entites.Customer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ICustomerService
{
    Customer getCustomerbyEmail(String email);
    Customer getCustomerByUsername(String username);
    List<Customer> getCustomersByCountryName(String countryName);
    List<Customer> getCustomersByCityName(String cityName);
    List<Customer> getCustomersByStreetName(String streetName);
    List<Customer> getCustomersByZipCode(int zipCode);

    List<Customer> getCustomersByRegistrationDate(LocalDate registrationDate);

    List<Customer> getCustomersByRegistrationTime(LocalTime registrationTime);
    List<Customer> getCustomersByRole(String Role);
    List<Customer> getCustomersByEnabled(Boolean enabled);
}
