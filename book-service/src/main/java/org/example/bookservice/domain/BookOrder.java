package org.example.bookservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.bookservice.dto.BuyBookDto;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookOrder {
    @NotNull
    private Double orderTotal;
    @NotNull
    private List<BuyBookDto> books;
    @NotBlank
    private String userDocument;
    @NotBlank
    private String cardNumber;
    @NotBlank
    private String cardCvv;
}
