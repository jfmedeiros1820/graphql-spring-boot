package com.joaomedeiros.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GraphqlSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlSpringBootApplication.class, args);
    }

}
