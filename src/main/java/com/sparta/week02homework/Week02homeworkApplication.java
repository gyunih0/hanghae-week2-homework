package com.sparta.week02homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week02homeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week02homeworkApplication.class, args);
    }

}
