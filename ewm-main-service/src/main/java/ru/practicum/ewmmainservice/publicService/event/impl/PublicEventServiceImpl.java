package ru.practicum.ewmmainservice.publicService.event.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.publicService.client.EwmClient;
import ru.practicum.ewmmainservice.publicService.event.PublicEventService;
import ru.practicum.ewmstatscontract.client.Client;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;

import java.util.Collection;
@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {
    private final EwmClient client;
    @Override
    public Collection<EventShortDto> findEfents() {
        EndpointHitDto endpointHitDto = new EndpointHitDto(1L,"app","dfdf","ip", "time");
        client.post("/hit", endpointHitDto);
        return null;
    }
}
