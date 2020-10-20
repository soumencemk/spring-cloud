package com.soumen.cloudstreamproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(HelloBinding.class)
public class CloudStreamProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamProducerApplication.class, args);
    }

}
