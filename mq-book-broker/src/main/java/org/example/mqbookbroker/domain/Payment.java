package org.example.mqbookbroker.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payment {
    private String userDocument;
    private String cardNumber;
    private String cardCvv;
}
