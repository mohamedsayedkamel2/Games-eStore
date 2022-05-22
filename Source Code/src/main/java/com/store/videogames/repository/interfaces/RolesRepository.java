package com.store.videogames.repository.interfaces;

import com.store.videogames.repository.entites.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>
{
    Roles getRolesByName(String name);
}
