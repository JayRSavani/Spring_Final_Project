package com.humber.final_project.repositories;

import com.humber.final_project.models.Claim;
import org.springframework.data.repository.CrudRepository;

public interface ClaimRepository extends CrudRepository<Claim, Integer> {
}
