package com.store.videogames.repository;

import com.store.videogames.entites.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RolesRepository extends JpaRepository<Roles, Long>
{
    Roles getRolesByName(String name);
}
