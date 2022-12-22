package com.belajar.belajarapilagi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseData<P> {
    private boolean status;
    private List<String>messages = new ArrayList<>();
    private P payload;

}