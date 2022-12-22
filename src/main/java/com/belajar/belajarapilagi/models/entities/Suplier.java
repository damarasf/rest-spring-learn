package com.belajar.belajarapilagi.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_suplier")
public class Suplier {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String address;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "supliers")
    private Set<Product> products;
}
