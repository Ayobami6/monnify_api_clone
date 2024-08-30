package com.ayo.monnify_api_clone.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    // Queue to listen
    @Bean
    public Queue emailQueue() {
        return new Queue("emailQueue", false);
    }

    // Exchange

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("emailExchange");
    }

    // Binding
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("emailRouting");
    }

}
