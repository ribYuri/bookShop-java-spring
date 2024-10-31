package org.example.bookservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", unique = true)
    private String name;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "amount_sold")
    private Integer amountSold = 0;
}
