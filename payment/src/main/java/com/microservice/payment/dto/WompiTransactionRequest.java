package com.microservice.payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WompiTransactionRequest {

    private String reference;
    private String currency;
    private Long amountInCents;
    private String customerEmail;
    private String paymentMethod; // CARD, PSE, NEQUI, etc.

    public WompiTransactionRequest() {
    }

    public WompiTransactionRequest(String reference, String currency, Long amountInCents, String customerEmail, String paymentMethod) {
        this.reference = reference;
        this.currency = currency;
        this.amountInCents = amountInCents;
        this.customerEmail = customerEmail;
        this.paymentMethod = paymentMethod;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmountInCents() {
        return amountInCents;
    }

    public void setAmountInCents(Long amountInCents) {
        this.amountInCents = amountInCents;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

