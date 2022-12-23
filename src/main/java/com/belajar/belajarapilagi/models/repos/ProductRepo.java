package com.belajar.belajarapilagi.models.repos;

import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.models.entities.Suplier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface ProductRepo extends CrudRepository <Product, Long> {

    @Query("select p from Product p where lower(p.name) like lower(concat('%', :name, '%'))")
    List<Product> findByName(@PathParam("name") String name);

    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> findByCategory(@PathParam("categoryId") Long categoryId);

    @Query("select p from Product p where :suplier member of p.supliers")
    List<Product>findBySuplier(@PathParam("suplier") Suplier suplier);

//    List<Product> findByNameContains(String name);
}
