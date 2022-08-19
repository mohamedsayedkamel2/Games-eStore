package com.store.videogames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = {"com.store.videogames.repository.entites", "com.store.videogames", "com.store.videogames.service", "com.store.videogames.repository"})
public class VideogamesStoreApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(VideogamesStoreApplication.class, args);
	}
}
