package com.pesto.authservice.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pesto.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class KafkaMessageListener {

    @Autowired
    UserService userservice;

    @KafkaListener(topics = "order-topic", groupId = "order-created-consumers")
    public void processOrderMessage(String message) {
        OrderMessage orderMessage;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            orderMessage = objectMapper.readValue(
                    message, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to deserialize message");
        }
        if (!Objects.isNull(orderMessage))
            userservice.processOrder(orderMessage);
    }
}
