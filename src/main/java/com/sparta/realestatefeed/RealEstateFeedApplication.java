package com.sparta.realestatefeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealEstateFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealEstateFeedApplication.class, args);
    }

}
