package org.example.mqbookbroker.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mqbookbroker.domain.BookOrder;
import org.example.mqbookbroker.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderQueueConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    public OrderQueueConsumer(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = {"order-request-queue"})
    public void receiveMessage(@Payload Message message) throws JsonProcessingException {
        BookOrder bookOrder = objectMapper.readValue((String) message.getPayload(), BookOrder.class);
        orderService.processOrder(bookOrder);
    }
}
