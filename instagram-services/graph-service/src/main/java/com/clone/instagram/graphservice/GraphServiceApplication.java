package com.clone.instagram.graphservice;

import com.clone.instagram.graphservice.messaging.UserEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableBinding(UserEventStream.class)
@EnableNeo4jRepositories
public class GraphServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(GraphServiceApplication.class, args);
    }
}
