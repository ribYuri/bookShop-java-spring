package org.example.bookservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookservice.dto.BuyBookDto;
import org.example.bookservice.service.BookService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class OrderQueueConsumer {
    private final ObjectMapper mapper;
    private final BookService bookService;

    public OrderQueueConsumer(ObjectMapper mapper, BookService bookService) {
        this.mapper = mapper;
        this.bookService = bookService;
    }

    @RabbitListener(queues = {"order-response-success-queue"})
    public void receiveMessage(@Payload Message message) throws JsonProcessingException {
        System.out.println("Order Books has been approved! Updating amounts sold in database...");
        List<BuyBookDto> books = Arrays.asList(mapper.readValue((String) message.getPayload(), BuyBookDto[].class));
        bookService.updateBooksSoldQuantity(books);
    }
}
