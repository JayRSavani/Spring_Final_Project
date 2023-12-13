package com.humber.final_project.repositories;

import com.humber.final_project.models.Claim;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends CrudRepository<Claim, Integer> {
    List<Claim> findByProductRegistrationUserUsername(@Param("username") String username);

}
