package com.example.lazada.service;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({ "c1","c2","c3","c4","c5","c6","c7","c8" })
public class CsvPojo {

    private String c1;
    private String c2;
    private String c3;
    private String c4;
    private String c5;
    private String c6;
    private String c7;
    private String c8;
}
