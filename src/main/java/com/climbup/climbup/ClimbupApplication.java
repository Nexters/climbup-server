package com.climbup.climbup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClimbupApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClimbupApplication.class, args);
    }

}
