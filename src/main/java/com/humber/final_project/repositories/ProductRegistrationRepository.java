package com.humber.final_project.repositories;

import com.humber.final_project.models.ProductRegistration;
import org.springframework.data.repository.CrudRepository;

public interface ProductRegistrationRepository extends CrudRepository<ProductRegistration, Integer> {
}
