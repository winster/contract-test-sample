package com.example.consumer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RabbitConfig {
    
    /*Following beans are only required for spring-cloud-contract to map exchange to a listener. But not required in `main`*/
    @Bean
    DirectExchange customEventExchange() {
        return ExchangeBuilder.directExchange("custom-event-exchange").build();
    }
    
    @Bean
    Queue customEventQueue() {
        return QueueBuilder.durable("custom-event-queue").build();
    }
    
    @Bean
    Binding customEventBinding() {
        return BindingBuilder.bind(customEventQueue())
                             .to(customEventExchange())
                             .with("routing-key");
    }
    
}
