package com.belajar.belajarapilagi.models.repos;

import com.belajar.belajarapilagi.models.entities.Suplier;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SuplierRepo extends CrudRepository<Suplier, Long> {

    Suplier findByEmail(String email);

    List<Suplier> findByNameContains(String name);

    List<Suplier> findByNameStartingWith(String prefix);

    List<Suplier> findByNameContainsOrEmailContains(String name, String email);
}
