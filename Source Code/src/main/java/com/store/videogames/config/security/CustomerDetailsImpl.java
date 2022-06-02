package com.store.videogames.config.security;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerDetailsImpl implements UserDetails
{

    private Customer customer;

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

    //I made it email currently until i make the user create a username
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
