package com.example.example.client;

import com.example.example.configuration.WebConstants;
import com.example.example.model.order.OrderWithMetaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Component
public class OrderClient {


    private final WebClient webClient;

    public OrderClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://order-service:" + Optional.ofNullable(System.getenv("SERVER_HTTP_PORT")).orElse("8081"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    public UUID getOrderIdByKey(String key) {
        return Objects.requireNonNull(this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/refs/{key}").build(key))
                .retrieve()
                .bodyToMono(Dto.class)
                .block()).id;
    }

    public void deleteKey(String key) {
        this.webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/refs/{key}").build(key))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public UUID getOrderIdByExternal(String vendorCode, String externalOrderId) {
        return Objects.requireNonNull(this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/external/{externalOrderId}").build(externalOrderId))
                .header(WebConstants.Header.EXTERNAL_SYSTEM, vendorCode)
                .retrieve()
                .bodyToMono(Dto.class)
                .block()).id;
    }


    public List<OrderWithMetaDto> getOrders(Collection<UUID> orders) {
        return Objects.requireNonNull(this.webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/list").build())
                .bodyValue(orders)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderWithMetaDto>>() {
                })
                .block());
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Dto {
        UUID id;
    }
}
