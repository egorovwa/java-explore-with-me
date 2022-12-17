package ru.practicum.ewmmainservice.publicservice.event.impl;

import com.example.evmdtocontract.dto.event.EventFullDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.client.EwmClient;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.publicservice.event.PublicEventService;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    private final EwmClient client;
    private final PrivateEventRepository repository;
    private final EventDtoMaper dtoMaper;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    @Value("${app.name}")
    private String appName;

    @Override
    public Collection<EventShortDto> findEvents(ParametersPublicEventFind param) { // TODO: 05.10.2022  интеграционных тестов
        if (param.getOnlyAvailable()) {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, param.getEndpointPath(), param.getClientIp(),
                    formatter.format(LocalDateTime.now()));
            try {
                client.post(endpointHitDto);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
log.info("Find events with parameters {}", param);
            return repository.findAllForPublicAvailable(param.getText(), param.getCatIds(), param.getPaid(),
                            param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        } else {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, param.getEndpointPath(), param.getClientIp(),
                    formatter.format(LocalDateTime.now()));
            try {
                client.post(endpointHitDto);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
            return repository.findAllForPublic(param.getText(), param.getCatIds(), param.getPaid(),
                            param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        }
    }

    @Override
    public EventFullDto findById(Long id, String requestURI, String remoteAddr) throws NotFoundException {
        EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, requestURI, remoteAddr,
                formatter.format(LocalDateTime.now()));
        try {
            client.post(endpointHitDto);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        log.info("Find event id ={}", id);
        return dtoMaper.toFulDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("id", id.toString(), "Event")));
    }
}
