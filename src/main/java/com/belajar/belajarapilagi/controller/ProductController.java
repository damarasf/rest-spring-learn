package com.belajar.belajarapilagi.controller;

import com.belajar.belajarapilagi.dto.ResponseData;
import com.belajar.belajarapilagi.dto.SearchData;
import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.models.entities.ProductPage;
import com.belajar.belajarapilagi.models.entities.ProductSearchCriteria;
import com.belajar.belajarapilagi.models.entities.Suplier;
import com.belajar.belajarapilagi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            ProductPage productPage,
            ProductSearchCriteria productSearchCriteria) {
        return new ResponseEntity<>(
                productService.getProducts(
                        productPage,
                        productSearchCriteria), HttpStatus.OK);
    }

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

    @PostMapping("/add-suplier/{productId}")
    public void addSuplier(@RequestBody Suplier suplier, @PathVariable Long productId){
        productService.addSuplier(suplier, productId);
    }

    @PostMapping("/search/name")
    public List<Product> getByName(@RequestBody SearchData searchData){
        return productService.findByName(searchData.getSearchKey());
    }

    @GetMapping("/search/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId){
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/search/suplier/{suplierId}")
    public List<Product> getBySuplier(@PathVariable Long suplierId){
        return productService.findBySuplier(suplierId);
    }
}
