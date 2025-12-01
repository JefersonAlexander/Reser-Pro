package com.microservice.payment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WompiTransactionResponse {
    private String id;
    private String status;
    private Long amountInCents;
    private String currency;
    private String reference;
}
