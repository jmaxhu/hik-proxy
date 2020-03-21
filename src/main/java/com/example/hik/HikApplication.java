package com.example.hik;

import com.example.hik.config.HikProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({HikProperties.class})
public class HikApplication {

    public static void main(String[] args) {
        SpringApplication.run(HikApplication.class, args);
    }

}
