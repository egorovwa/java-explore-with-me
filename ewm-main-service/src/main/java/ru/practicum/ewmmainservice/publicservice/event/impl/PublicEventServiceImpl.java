package ru.practicum.ewmmainservice.publicservice.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.client.EwmClient;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;
import ru.practicum.ewmmainservice.publicservice.event.PublicEventService;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    private final EwmClient client;
    private final PrivateEventRepository repository;
    private final EventDtoMaper dtoMaper;
    private final LocationService locationService;
    private final DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    @Value("${app.name}")
    private String appName;

    @Override
    public Collection<EventShortDto> findEvents(ParametersPublicEventFind param) {
        List<Long> locationsIds;
        if (param.isWithChilds()) {
            locationsIds = findChilds(param.getCatIds(), new ArrayList<Long>(), false);
        } else {
            locationsIds = param.getLocIds();
        }
        if (param.getOnlyAvailable()) {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, param.getEndpointPath(), param.getClientIp(),
                    formatter.format(LocalDateTime.now()));
            try {
                client.post(endpointHitDto);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
            log.info("Find events with parameters {}", param);
            return repository.findAllForPublicAvailable(param.getText(), param.getCatIds(), locationsIds, param.getPaid(),
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
            return repository.findAllForPublic(param.getText(), param.getCatIds(), locationsIds, param.getPaid(),
                            param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        }
    }

    private List<Long> findChilds(List<Long> finded, List<Long> toFind, boolean notAded) {

        if (notAded) {
            return finded;
        } else {
            List<Long> childs = toFind.stream().flatMap(r ->
                    {
                        try {
                            return locationService.findLocation(r).getChilds().stream().map(Location::getId);
                        } catch (NotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
            ).collect(Collectors.toList());
            finded.addAll(toFind);
            notAded = childs.isEmpty();
            return findChilds(finded, childs, notAded);
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
