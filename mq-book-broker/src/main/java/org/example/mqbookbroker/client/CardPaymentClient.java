package org.example.mqbookbroker.client;

import org.example.mqbookbroker.domain.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CardPaymentClient {
    private final List<String> approvedCards = Arrays.asList("5555555555555551", "4444444444444441");

    public ResponseEntity<Boolean> isPaymentApproved(Payment payment) {
        if (this.approvedCards.stream().anyMatch(card -> card.equalsIgnoreCase(payment.getCardNumber()))) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
