package org.example.mqbookbroker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mqbookbroker.client.CardPaymentClient;
import org.example.mqbookbroker.constants.OrderStatus;
import org.example.mqbookbroker.domain.BookOrder;
import org.example.mqbookbroker.domain.Payment;
import org.example.mqbookbroker.entity.OrderEntity;
import org.example.mqbookbroker.producer.OrderSuccessQueue;
import org.example.mqbookbroker.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CardPaymentClient cardPaymentClient;
    private final OrderSuccessQueue orderSuccessQueue;
    private final ObjectMapper mapper;

    public OrderService(OrderRepository orderRepository, CardPaymentClient cardPaymentClient, OrderSuccessQueue orderSuccessQueue, ObjectMapper mapper) {
        this.orderRepository = orderRepository;
        this.cardPaymentClient = cardPaymentClient;
        this.orderSuccessQueue = orderSuccessQueue;
        this.mapper = mapper;
    }

    public void processOrder(BookOrder bookOrder) {
        Payment orderPayment = getPayment(bookOrder);
        Boolean isPaymentApproved = cardPaymentClient.isPaymentApproved(orderPayment).getBody();
        OrderEntity orderEntity = orderRepository.save(buildOrderEntity(bookOrder));
        if (isPaymentApproved){
            orderEntity.setStatus(OrderStatus.CONCLUDED.getValue());
            orderRepository.save(orderEntity);
            System.out.println("Order approved! User: " + orderEntity.getUserDocument());
            orderSuccessQueue.sendToQueue(orderEntity.getBooks());
        } else {
            orderEntity.setStatus(OrderStatus.REJECTED.getValue());
            orderRepository.save(orderEntity);
            System.out.println("Order Rejected. Payment not approved. User: " + orderEntity.getUserDocument());
        }
    }

    private OrderEntity buildOrderEntity(BookOrder bookOrder) {
        try {
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setOrderTotal(bookOrder.getOrderTotal());
            orderEntity.setStatus(OrderStatus.RECEIVED.getValue());
            orderEntity.setUserDocument(bookOrder.getUserDocument());
            orderEntity.setCardNumber(bookOrder.getCardNumber());
            orderEntity.setCardCvv(bookOrder.getCardCvv());
            orderEntity.setBooks(mapper.writeValueAsString(bookOrder.getBooks()));
            return orderEntity;
        } catch (JsonProcessingException e) {
            System.out.println("Error building Order Entity!");
            return null;
        }
    }

    private static Payment getPayment(BookOrder bookOrder) {
        Payment orderPayment = Payment.builder()
                .cardNumber(bookOrder.getCardNumber())
                .cardCvv(bookOrder.getCardCvv())
                .userDocument(bookOrder.getUserDocument())
                .build();
        return orderPayment;
    }

}
