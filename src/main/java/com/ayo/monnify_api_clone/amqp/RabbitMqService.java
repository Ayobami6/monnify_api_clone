package com.ayo.monnify_api_clone.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RabbitMqService {


    private final RabbitTemplate rabbitTemplate;

    public void sendMessageToMailQueue(Object message) {
        rabbitTemplate.convertAndSend("emailExchange", "emailRouting", message);
    }

}
