package org.example.mqbookbroker.constants;

import lombok.Getter;

@Getter
public enum OrderStatus {
    RECEIVED("received"),
    CONCLUDED("concluded"),
    REJECTED("rejected");

    private String value;

    private OrderStatus(String value) {
        this.value = value;
    }
}
