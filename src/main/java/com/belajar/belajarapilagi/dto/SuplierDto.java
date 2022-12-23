package com.belajar.belajarapilagi.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class SuplierDto {

    @NotEmpty (message = "Nama Suplier tidak boleh kosong")
    private String name;

    @NotEmpty (message = "Alamat Suplier tidak boleh kosong")
    private String address;

    @NotEmpty (message = "Email Suplier tidak boleh kosong")
    @Email (message = "Email Suplier tidak valid")
    private String email;
}
