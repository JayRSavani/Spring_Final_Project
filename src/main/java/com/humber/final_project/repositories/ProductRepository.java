package com.humber.final_project.repositories;

import com.humber.final_project.models.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Products, Integer> {

    Products findByName(String name);
}
