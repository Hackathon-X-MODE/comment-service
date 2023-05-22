package com.example.example.client;

import com.example.example.model.CommentDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
public class TicketClient {


    private final WebClient webClient;

    public TicketClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://ticket-service:" + Optional.ofNullable(System.getenv("SERVER_HTTP_PORT")).orElse("8084"))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }


    public void registerTicket(CommentDto commentDto) {
        this.webClient.post()
                .uri(uriBuilder -> uriBuilder.path("/notify").build())
                .bodyValue(commentDto)
                .retrieve()
                .toBodilessEntity().block();
    }
}
