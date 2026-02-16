package com.mohammad.relief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.mohammad.relief")
@EnableScheduling
@EnableJpaAuditing
public class ReliefApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReliefApplication.class, args);

    }

}
