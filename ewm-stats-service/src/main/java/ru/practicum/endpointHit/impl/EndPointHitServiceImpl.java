package ru.practicum.endpointHit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.endpointHit.EndPointHitService;
import ru.practicum.endpointHit.EndpointHitRepository;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStatsDto;
import ru.practicum.models.EndpointHitDtoMaper;
import ru.practicum.models.ParamViewStats;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndPointHitServiceImpl implements EndPointHitService {
    private final EndpointHitRepository repository;
    private final EndpointHitDtoMaper dtoMaper;
    @Value("${stats-servece.name}")
    String appName;
    @Value("${ewm-main-service.url}")
    String urlMainService;

    @Override
    public EndpointHitDto save(EndpointHitDto endpointHitDto) {
        return dtoMaper.toDto(repository.save(dtoMaper.fromDto(endpointHitDto)));
    }

    @Override
    public Collection<ViewStatsDto> getStat(ParamViewStats param) {
        if (param.getUnique()) {
            return param.getUris().stream().map(uri -> {
                return new ViewStatsDto(appName, uri, repository.getHitCountUnique(param.getStart(), param.getEnd(), uri));
            }).collect(Collectors.toList());
        } else {
            return param.getUris().stream().map(uri -> {
                return new ViewStatsDto(appName, uri, repository.getHitCountAll(param.getStart(), param.getEnd(), uri));
            }).collect(Collectors.toList());
        }
    }
}
