package com.example.example.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.UUID;

@Component
public class PostamatClient {

    private final WebClient webClient;

    public PostamatClient() {
        this.webClient = WebClient.builder()
                .baseUrl("http://vendor-service:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Nullable
    public UUID getPostamatId(UUID vendorId, String externalId) {
        try {
            return Objects.requireNonNull(this.webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("{vendorId}/postamats/externalIds/{externalId}").build(vendorId, externalId))
                    .retrieve()
                    .bodyToMono(PostamatDto.class)
                    .block()).id;
        } catch (Exception e) {
            return null;
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class PostamatDto {
        private UUID id;
    }
}
