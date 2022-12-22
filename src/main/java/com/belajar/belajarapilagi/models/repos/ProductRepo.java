package com.belajar.belajarapilagi.models.repos;

import com.belajar.belajarapilagi.models.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepo extends CrudRepository <Product, Long> {

    List<Product> findByNameContains(String name);
}
