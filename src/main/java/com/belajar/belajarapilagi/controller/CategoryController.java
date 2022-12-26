package com.belajar.belajarapilagi.controller;

import com.belajar.belajarapilagi.dto.CategoryDto;
import com.belajar.belajarapilagi.dto.ResponseData;
import com.belajar.belajarapilagi.dto.SearchData;
import com.belajar.belajarapilagi.models.entities.Category;
import com.belajar.belajarapilagi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping("/api/category")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<ResponseData<Category>> create(@Valid @RequestBody CategoryDto categoryDto, Errors errors) {

        ResponseData<Category> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }

        Category category = modelMapper.map(categoryDto, Category.class);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.save(category));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<Category>> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto, Errors errors) {

        ResponseData<Category> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }

        Category category = modelMapper.map(categoryDto, Category.class);
        category.setId(id);

        responseData.setStatus(true);
        responseData.setPayload(categoryService.update(id, category));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/{size}/{page}")
    public Iterable<Category> findByNameContains(@RequestBody SearchData searchData, @PathVariable int size, @PathVariable int page) {

        Pageable pageable = PageRequest.of(page, size);
        return categoryService.findByNameContains(searchData.getSearchKey(), pageable);
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Category> findByNameContains(@RequestBody SearchData searchData, @PathVariable int size, @PathVariable int page, @PathVariable String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (sort.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        }
        return categoryService.findByNameContains(searchData.getSearchKey(), pageable);
    }


    @PostMapping("/batch")
    public ResponseEntity<ResponseData<Iterable<Category>>> createBatch(@RequestBody Category[] categories) {

        ResponseData<Iterable<Category>> responseData = new ResponseData<>();
        responseData.setPayload(categoryService.saveBatch(Arrays.asList(categories)));
        responseData.setStatus(true);
        return ResponseEntity.ok(responseData);
    }
}
