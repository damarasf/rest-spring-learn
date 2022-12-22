package com.belajar.belajarapilagi.controller;

import com.belajar.belajarapilagi.dto.ResponseData;
import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private  ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ResponseData<Product>> create(@Valid @RequestBody Product product, Errors errors) {

        ResponseData<Product> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.save(product));
        return ResponseEntity.ok(responseData);
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
    public ResponseEntity<ResponseData<Product>> update(@Valid @PathVariable Long id, @RequestBody Product product, Errors errors) {

        ResponseData<Product> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        responseData.setStatus(true);
        responseData.setPayload(productService.update(id, product));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }
}
