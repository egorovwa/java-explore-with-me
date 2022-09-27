package ru.practicum.ewmstatscontract.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class Client {
    WebClient webClient = WebClient.create("http://localhost:9090");

    public ResponseEntity<Object> post(String path, Object object) {
        return  webClient
                .post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(object), Object.class)
                .retrieve()
                .toEntity(Object.class)
                .block();


    }

}
