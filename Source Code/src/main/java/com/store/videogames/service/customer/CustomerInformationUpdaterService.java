package com.store.videogames.service.customer;

import com.store.videogames.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerInformationUpdaterService
{
    private final CustomerRepository customerRepository;
    @Autowired
    CustomerInformationUpdaterService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @CachePut("Customer")
    @Transactional
    public void changeCustomerEnabledState(int id, boolean newEnableStatus)
    {
        customerRepository.updateEnabledStatus(id,newEnableStatus);
    }
}
