package com.humber.final_project.repositories;

import com.humber.final_project.models.ProductRegistration;
import com.humber.final_project.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRegistrationRepository extends CrudRepository<ProductRegistration, Integer> {
        List<ProductRegistration> findByUser(Users user);
}
