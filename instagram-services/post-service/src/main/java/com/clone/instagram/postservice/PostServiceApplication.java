package com.clone.instagram.postservice;

import com.clone.instagram.postservice.messaging.PostEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoAuditing
@EnableBinding(PostEventStream.class)
public class PostServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }
}
