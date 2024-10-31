package org.example.bookservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookservice.domain.BookOrder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderQueueProducer {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    public OrderQueueProducer(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendToQueue(BookOrder bookOrder) throws JsonProcessingException {
        amqpTemplate.convertAndSend("order-request-exchange", "order-request-rout-key", objectMapper.writeValueAsString(bookOrder));
    }
}
