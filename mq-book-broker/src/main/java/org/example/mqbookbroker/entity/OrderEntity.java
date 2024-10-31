package org.example.mqbookbroker.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.mqbookbroker.constants.OrderStatus;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    @Column
    private String status;
    @Column(name = "order_total")
    private Double orderTotal;
    @Column(name = "user_document")
    private String userDocument;
    @Column(name = "books")
    private String books;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "card_cvv")
    private String cardCvv;
}
