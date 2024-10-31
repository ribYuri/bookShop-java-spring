package org.example.mqbookbroker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.example.mqbookbroker.dto.BuyBookDto;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookOrder {
    private Double orderTotal;
    private List<BuyBookDto> books;
    private String userDocument;
    private String cardNumber;
    private String cardCvv;
}
