package com.humber.final_project.repositories;

import com.humber.final_project.models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Users, Integer> {

    Users findByEmail(String email);
    Users findByUsername(String username);
}


