package com.belajar.belajarapilagi.service;

import com.belajar.belajarapilagi.models.entities.Category;
import com.belajar.belajarapilagi.models.repos.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public  Category save(Category category){
        return categoryRepo.save(category);
    }

    public Iterable<Category> findAll(){
        return categoryRepo.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            return category.get();
        }
        return null;
    }

    public void delete(Long id){
        categoryRepo.deleteById(id);
    }

    public Category update(Long id, Category category){
        Category category1 = findById(id);
        category1.setName(category.getName());
        return categoryRepo.save(category1);
    }

    public Iterable<Category> findByNameContains(String name, Pageable pageable){
        return categoryRepo.findByNameContains(name, pageable);
    }

    public Iterable<Category> saveBatch(Iterable<Category> categories){
        return categoryRepo.saveAll(categories);
    }
}
