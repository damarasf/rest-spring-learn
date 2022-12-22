package com.belajar.belajarapilagi.controller;

import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private  ProductService productService;

    @PostMapping("/create")
    public Product create(@RequestBody Product product){
        return productService.save(product);
    }

    @GetMapping
    public Iterable<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id){
        return productService.findById(id);
    }

    @PutMapping("/update/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product){
        return productService.update(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}
