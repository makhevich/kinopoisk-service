package ru.abrosimov.kinopoiskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class KinopoiskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KinopoiskServiceApplication.class, args);
    }

    // Бин для совершения HTTP-запросов к внешним серверам
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}