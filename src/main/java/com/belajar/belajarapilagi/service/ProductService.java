package com.belajar.belajarapilagi.service;

import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.models.entities.ProductPage;
import com.belajar.belajarapilagi.models.entities.ProductSearchCriteria;
import com.belajar.belajarapilagi.models.entities.Suplier;
import com.belajar.belajarapilagi.models.repos.ProductCriteriaRepo;
import com.belajar.belajarapilagi.models.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private final ProductRepo productRepo;

    private final ProductCriteriaRepo productCriteriaRepo;

    @Autowired
    private SuplierService suplierService;

    public ProductService(ProductRepo productRepo, ProductCriteriaRepo productCriteriaRepo) {
        this.productRepo = productRepo;
        this.productCriteriaRepo = productCriteriaRepo;
    }

    public Page<Product> getProducts(
            ProductPage productPage,
            ProductSearchCriteria productSearchCriteria) {

        return productCriteriaRepo.findAll(productPage, productSearchCriteria);
    }

    public Product save(Product product) {
        return productRepo.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    public Iterable<Product> findAll() {
        return productRepo.findAll();
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public Product update(Long id, Product product) {
        Product product1 = productRepo.findById(id).get();
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        return productRepo.save(product1);
    }

    /*public List<Product> findByNameContains(String name) {
        return productRepo.findByNameContains(name);
    }*/

    public void addSuplier(Suplier suplier, Long productId) {
        Product product = findById(productId);
        if (product != null) {
            product.getSupliers().add(suplier);
            productRepo.save(product);
        } else {
            throw new RuntimeException("Product with ID: "+productId+" not found");
        }
        product.getSupliers().add(suplier);
        save(product);
    }

    public List<Product> findByName(String name) {
        return productRepo.findByName(name);
    }

    public List<Product> findByCategory(Long categoryId) {
        return productRepo.findByCategory(categoryId);
    }

    public List<Product> findBySuplier(Long suplierId) {
        Suplier suplier = suplierService.findById(suplierId);

        if (suplier != null) {
            return productRepo.findBySuplier(suplier);
        } else {
            return new ArrayList<Product>();
        }
    }
}
