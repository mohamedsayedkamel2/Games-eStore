package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>
{
}
