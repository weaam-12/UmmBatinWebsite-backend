package com.ummbatin.service_management.dtos;

public class PaymentRequest {
    private Long amount;  // amount in cents

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
