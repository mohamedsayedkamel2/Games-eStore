package com.store.videogames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VideogamesStoreApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(VideogamesStoreApplication.class, args);
	}

}
