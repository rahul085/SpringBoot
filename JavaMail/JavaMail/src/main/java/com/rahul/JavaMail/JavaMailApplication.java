package com.rahul.JavaMail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaMailApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaMailApplication.class);
    }
}
