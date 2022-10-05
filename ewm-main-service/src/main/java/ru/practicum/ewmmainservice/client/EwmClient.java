package ru.practicum.ewmmainservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmstatscontract.client.Client;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;

@Service
@Slf4j
public class EwmClient extends Client {
    @Value("${save-hit.endpoint}")
    String saveHitEndpoint;

    public EwmClient(@Value("${stasistic-server.url}") String baseUrl) {
        super(baseUrl);
    }


    public void post(EndpointHitDto endpointHitDto) {  // TODO: 05.10.2022  @Transactional ???
        ResponseEntity<Object> response = super.post(saveHitEndpoint, endpointHitDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Statistic saved: {}", response);
        } else {
            log.error("Error statistic server Http code: {}", response.getStatusCodeValue());
        }
    }
}
