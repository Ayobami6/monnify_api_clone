package com.ayo.monnify_api_clone.amqp;

import java.io.IOException;
import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.ayo.monnify_api_clone.common.services.EmailService;
import com.ayo.monnify_api_clone.exception.InternalServerException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final EmailService emailService;

    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "emailQueue")
    public void receiveMessage(Message message) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> mailData = objectMapper.readValue(message.getBody(), Map.class);

            // Extract email details from the map
            String email = mailData.get("email");
            String otp = mailData.get("otp");
            String subject = "Email Verification";
            String body = "Your OTP code is: " + otp;

            // Send the email
            emailService.sendOTPMail(email, subject, body);

            System.out.println("Received and processed message: ");
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerException();
        }
    }


}
