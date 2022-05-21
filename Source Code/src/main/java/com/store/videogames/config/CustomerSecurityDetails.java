package com.store.videogames.config;

import com.store.videogames.repository.entites.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CustomerSecurityDetails implements UserDetails
{

    private Customer customer;

    public CustomerSecurityDetails(Customer customer)
    {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(customer.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword()
    {
        return customer.getPassword();
    }

    //I made it email currently until i make the user create a username
    @Override
    public String getUsername()
    {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return customer.getEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return customer.getEnabled();
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
