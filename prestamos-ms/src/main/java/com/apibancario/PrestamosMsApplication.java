package com.apibancario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrestamosMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrestamosMsApplication.class, args);
    }
}