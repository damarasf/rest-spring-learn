package com.belajar.belajarapilagi.service;

import com.belajar.belajarapilagi.models.entities.Suplier;
import com.belajar.belajarapilagi.models.repos.SuplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SuplierService {

    @Autowired
    private SuplierRepo suplierRepo;

    public Suplier save(Suplier suplier) {
        return suplierRepo.save(suplier);
    }

    public Iterable<Suplier> findAll(){
        return suplierRepo.findAll();
    }

    public Suplier findById(Long id) {
        Optional<Suplier> suplier = suplierRepo.findById(id);
        if (suplier.isPresent()) {
            return suplier.get();
        }
        return null;
    }

    public void delete(Long id){
        suplierRepo.deleteById(id);
    }

    public Suplier update(Long id, Suplier suplier){
        Suplier suplier1 = findById(id);
        suplier1.setName(suplier.getName());
        return suplierRepo.save(suplier1);
    }
}
