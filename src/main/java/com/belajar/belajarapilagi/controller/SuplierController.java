package com.belajar.belajarapilagi.controller;

import com.belajar.belajarapilagi.dto.ResponseData;
import com.belajar.belajarapilagi.dto.SearchData;
import com.belajar.belajarapilagi.dto.SuplierDto;
import com.belajar.belajarapilagi.models.entities.Suplier;
import com.belajar.belajarapilagi.service.SuplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suplier")
public class SuplierController {

    @Autowired
    private SuplierService suplierService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<ResponseData<Suplier>> create(@Valid @RequestBody SuplierDto suplierDto, Errors errors) {

        ResponseData<Suplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        /*Suplier suplier = new Suplier();
        suplier.setName(suplierDto.getName());
        suplier.setAddress(suplierDto.getAddress());
        suplier.setEmail(suplierDto.getEmail());*/

        Suplier suplier = modelMapper.map(suplierDto, Suplier.class);

        responseData.setStatus(true);
        responseData.setPayload(suplierService.save(suplier));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Suplier> findAll(){
        return suplierService.findAll();
    }

    @GetMapping("/{id}")
    public Suplier findById(@PathVariable Long id){
        return suplierService.findById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseData<Suplier>> update(@Valid @PathVariable Long id, @RequestBody SuplierDto suplierDto, Errors errors) {

        ResponseData<Suplier> responseData = new ResponseData<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.badRequest().body(responseData);
        }
        Suplier suplier = modelMapper.map(suplierDto, Suplier.class);

        responseData.setStatus(true);
        responseData.setPayload(suplierService.update(id, suplier));
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/search/email")
    public Suplier findByEmail(@RequestBody SearchData searchData){
        return suplierService.findByEmail(searchData.getSearchKey());
    }

    @PostMapping("/search/name")
    public List<Suplier> findByName(@RequestBody SearchData searchData){
        return suplierService.findByName(searchData.getSearchKey());
    }

    @PostMapping("/search/prefix")
    public List<Suplier> findByNameStartingWith(@RequestBody SearchData searchData){
        return suplierService.findByNamePrefix(searchData.getSearchKey());
    }

    @PostMapping("/search/contains")
    public List<Suplier> findByNameContainsOrEmailContains(@RequestBody SearchData searchData){
        return suplierService.findByNameOrEmail(searchData.getSearchKey(), searchData.getOtherKey());
    }
}
