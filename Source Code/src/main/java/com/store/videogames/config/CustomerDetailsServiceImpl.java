package com.store.videogames.config;

import com.store.videogames.config.CustomerSecurityDetails;
import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.interfaces.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomerDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Customer customer = customerRepository.getConfigUsername(username);

        if (customer == null)
        {
            throw new UsernameNotFoundException("Couldn't find the username");
        }
        return new CustomerSecurityDetails(customer);
    }
}
