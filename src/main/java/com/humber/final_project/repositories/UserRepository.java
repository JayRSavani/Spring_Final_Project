package com.humber.final_project.repositories;

import com.humber.final_project.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}


