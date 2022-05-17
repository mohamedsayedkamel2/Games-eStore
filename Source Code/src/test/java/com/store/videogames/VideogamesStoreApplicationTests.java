package com.store.videogames;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.Videogame;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.repository.interfaces.VideogameRepository;
import com.store.videogames.services.VideogameService;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@SpringBootTest
class VideogamesStoreApplicationTests
{
	@Autowired
	CustomerRepository customerRepository;

	@Test
	void contextLoads()
	{
		List<Customer> customer = customerRepository.getCustomerByEnabled(true);
		System.out.println(customer);
	}

}
