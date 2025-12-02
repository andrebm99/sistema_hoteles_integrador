package com.springboot.sistema.hoteles.springboot_sistemahoteles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainSistemaHoteles {
    public static void main(String[] args) {
        SpringApplication.run(MainSistemaHoteles.class, args);
        System.out.println("URL: http://localhost:8081");
    }
}
