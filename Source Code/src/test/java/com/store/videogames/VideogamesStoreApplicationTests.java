package com.store.videogames;

import com.store.videogames.repository.entites.Customer;
import com.store.videogames.repository.entites.DigitalVideogameCode;
import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.interfaces.CustomerRepository;
import com.store.videogames.repository.interfaces.DigitalVideogameCodeRepository;
import com.store.videogames.repository.interfaces.RolesRepository;
import com.store.videogames.repository.interfaces.VideogameRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class VideogamesStoreApplicationTests
{
	@Test
	void contextLoads()
	{
	}

}
