package com.giret.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApiGiretBFF {
    public static void main(String[] args) {
        SpringApplication.run(ApiGiretBFF.class, args);
    }
}
