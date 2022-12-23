package com.belajar.belajarapilagi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class CategoryDto {

    @NotEmpty (message = "Nama Kategori tidak boleh kosong")
    private String name;
}
