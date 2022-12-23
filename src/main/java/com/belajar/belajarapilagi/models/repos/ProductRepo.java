package com.belajar.belajarapilagi.models.repos;

import com.belajar.belajarapilagi.models.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ProductRepo extends CrudRepository <Product, Long> {

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :name, '%'))")
    List<Product> findByName(@PathParam("name") String name);

//    List<Product> findByNameContains(String name);
}
