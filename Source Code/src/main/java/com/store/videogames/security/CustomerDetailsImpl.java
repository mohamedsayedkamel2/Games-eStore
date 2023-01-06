package com.store.videogames.security;

import com.store.videogames.entites.Customer;
import com.store.videogames.entites.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerDetailsImpl implements UserDetails
{
    private final Customer customer;

    @Autowired
    public CustomerDetailsImpl(Customer customer)
    {
        this.customer = customer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<Roles>roles = customer.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return customer.getPassword();
    }

    @Override
    public String getUsername()
    {
        return customer.getEmail();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return customer.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return customer.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return customer.isEnabled();
    }

    public Customer getCustomer()
    {
        return customer;
    }
}
