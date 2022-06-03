package com.store.videogames.repository;

import com.store.videogames.repository.entites.Roles;
import com.store.videogames.repository.interfaces.RolesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RolesRepositoryTest
{
    @Autowired
    private RolesRepository rolesRepository;

    @Test
    public void getAllRoles()
    {
        rolesRepository.findAll();
    }

    @Test
    public void createRole()
    {
        Roles roles = new Roles();
        roles.setName("ADMIN");
        roles.setDescription("Whatever");
        rolesRepository.save(roles);
    }

    @Test
    public void removeRole()
    {
        rolesRepository.deleteById(1L);
    }
}
