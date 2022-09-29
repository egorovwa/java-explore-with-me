package ru.practicum.ewmstatscontract.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Client {
    private final WebClient webClient;
    public Client(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public ResponseEntity<Object> post(String path, Object object) {
        return webClient
                .post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(object), Object.class)
                .retrieve()
                .toEntity(Object.class)
                .onErrorReturn(ResponseEntity.internalServerError().build())
                .block();


    }

}
