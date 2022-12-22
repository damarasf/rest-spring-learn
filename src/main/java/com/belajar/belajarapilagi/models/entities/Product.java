package com.belajar.belajarapilagi.models.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name", length = 50)
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Column(name = "description", length = 100)
    private String description;

    private Double price;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(name = "tbl_product_suplier",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "suplier_id"))
    private Set<Suplier> supliers;
}
