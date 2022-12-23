package com.belajar.belajarapilagi.service;

import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.models.entities.Suplier;
import com.belajar.belajarapilagi.models.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

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

    public List<Product> findByNameContains(String name) {
        return productRepo.findByNameContains(name);
    }

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
}
