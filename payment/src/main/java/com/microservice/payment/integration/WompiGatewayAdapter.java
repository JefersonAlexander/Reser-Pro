package com.microservice.payment.integration;

import com.microservice.payment.dto.WompiTransactionRequest;
import com.microservice.payment.dto.WompiTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WompiGatewayAdapter {

    private final WebClient wompiWebClient;

    @Autowired
    public WompiGatewayAdapter(WebClient wompiWebClient) {
        this.wompiWebClient = wompiWebClient;
    }

    @Value("${wompi.api.public-key}")
    private String publicKey;

    @Value("${wompi.api.private-key}")
    private String privateKey;

    /**
     * Crear una transacción en Wompi
     */
    public WompiTransactionResponse createTransaction(WompiTransactionRequest request) {
        return wompiWebClient.post()
                .uri("/transactions")
                .header("Authorization", "Bearer " + privateKey)
                .body(Mono.just(request), WompiTransactionRequest.class)
                .retrieve()
                .bodyToMono(WompiTransactionResponse.class)
                .block();
    }

    /**
     * Consultar una transacción por su ID en Wompi
     */
    public WompiTransactionResponse getTransactionById(String wompiTransactionId) {
        return wompiWebClient.get()
                .uri("/transactions/{id}", wompiTransactionId)
                .retrieve()
                .bodyToMono(WompiTransactionResponse.class)
                .block();
    }
}

