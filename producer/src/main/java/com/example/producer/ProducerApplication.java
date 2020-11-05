package com.example.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.springframework.amqp.core.MessageProperties.*;

@SpringBootApplication
public class ProducerApplication {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    @Bean
    public DirectExchange customEventExchange() {
        return ExchangeBuilder.directExchange("custom-event-exchange").build();
    }
    
    @Bean
    public Queue customEventQueue() {
        return QueueBuilder.durable("custom-event-queue").build();
    }
    
    @Bean
    public Binding customEventBinding(DirectExchange customEventExchange, Queue customEventQueue) {
        return BindingBuilder.bind(customEventQueue)
                             .to(customEventExchange)
                             .with("routing-key");
    }
    
    @Bean
    public MessageConverter messageConverter() {
        final Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter(new ObjectMapper());
        jsonMessageConverter.setCreateMessageIds(true);
        final ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter(
            jsonMessageConverter);
        messageConverter.addDelegate(CONTENT_TYPE_JSON, jsonMessageConverter);
        return messageConverter;
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
    
    public void publishEvent() {
        System.out.println("publishEvent::");
        try {
            A a = new A();
            a.value = "AAA";
            rabbitTemplate.convertAndSend("custom-event-exchange", "routing-key", a);
        } catch (AmqpException e) {
            System.out.println("notifySendFailedEvents :: error " + e.getMessage());
        }
    }
    
    static class A {
        public String value;
    }
}
