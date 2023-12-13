package com.humber.final_project.repositories;

import com.humber.final_project.models.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Products, Integer> {
    Products findById (int id);
}
