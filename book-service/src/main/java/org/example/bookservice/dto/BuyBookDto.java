package org.example.bookservice.dto;

import lombok.Data;
import org.example.bookservice.entity.BookEntity;

@Data
public class BuyBookDto {
    private BookEntity book;
    private Integer quantity;
}
