package com.ayo.monnify_api_clone.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "emailQueue")
    public void receiveMessage(Message message) {
        System.out.println("Received message: " + message.getBody());
    }


}
