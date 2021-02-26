package com.example.lazada;

import com.example.lazada.service.ApacheCsvFileService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LazadaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LazadaApplication.class, args);

        ApacheCsvFileService apacheCsvReader = new ApacheCsvFileService();
        apacheCsvReader.process();
    }

}
