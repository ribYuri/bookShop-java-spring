package org.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookDto {
    @NotBlank
    private String name;
    @NotNull
    private Double price;
}
