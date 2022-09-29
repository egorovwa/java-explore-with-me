package ru.practicum.ewmmainservice.publicService.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.publicService.client.EwmClient;
import ru.practicum.ewmmainservice.publicService.event.PublicEventService;
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
    private final DateTimeFormatter formatter = Utils.getDateTimeFormater();

    @Value("${ewm-main-service.url}")
    private  String url;

    @Override
    public Collection<EventShortDto> findEvents(ParametersPublicEventFind param) {
        if (param.getOnlyAvailable()) {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null,url, param.getEndpointPath(), param.getClientIp(),
                    formatter.format(LocalDateTime.now()));
            client.post(endpointHitDto);
            return repository.findAllForPublicAvailable(param.getText(), param.getCatIds(), param.getPaid(),
                    param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        }else {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null,url, param.getEndpointPath(), param.getClientIp(),
                    formatter.format(LocalDateTime.now()));
            client.post(endpointHitDto);
            return repository.findAllForPublic(param.getText(), param.getCatIds(), param.getPaid(),
                    param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        }
    }

    @Override
    public EventFullDto findById(Long id, String requestURI, String remoteAddr) throws NotFoundException {
        client.post(new EndpointHitDto(null,url, requestURI, remoteAddr, formatter.format(LocalDateTime.now())));
        return dtoMaper.toFulDto(repository.findById(id)
                .orElseThrow(()-> new NotFoundException("id", id.toString(),"Event")));
    }
}
