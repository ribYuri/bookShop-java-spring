package org.example.mqbookbroker.dto;

import lombok.Data;

@Data
public class BuyBookDto {
    private Book book;
    private Integer quantity;
}
