package ru.practicum.ewmstatscontract.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class Client {
    WebClient webClient;
    public Mono<Object> post(String path, Object object){
        return webClient
                .post()
                .uri("",path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(Object.class);

    }

}
