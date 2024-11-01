package org.example.mqbookbroker.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderSuccessQueue {
    private final AmqpTemplate amqpTemplate;

    public OrderSuccessQueue(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendToQueue(String books) {
        amqpTemplate.convertAndSend("order-response-success-exchange", "", books);
    }
}
