package com.soumen.cloudstreamproducer;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    private MessageChannel greet;

    public ProducerController(HelloBinding binding) {
        this.greet = binding.greeting();
    }

    @GetMapping("/greet/{name}")
    public void publish(@PathVariable String name) {

        String greetingMsg = "Hello " + name;
        Message<String> message = MessageBuilder.withPayload(greetingMsg).build();
        this.greet.send(message);

    }
}
