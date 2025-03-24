package com.mohammad.relief;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(scanBasePackages = "com.mohammad.relief")
@EnableScheduling
public class ReliefApplication {

    public static void main(String[] args) {

        SpringApplication.run(ReliefApplication.class, args);

    }

}
