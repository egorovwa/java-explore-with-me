package ru.practicum.endpointHit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.endpointHit.EndPointHitService;
import ru.practicum.endpointHit.EndpointHitRepository;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStatsDto;
import ru.practicum.models.EndpointHitDtoMaper;
import ru.practicum.models.ParamViewStats;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndPointHitServiceImpl implements EndPointHitService {
    private final EndpointHitRepository repository;
    private final EndpointHitDtoMaper dtoMaper;
    @Override
    public EndpointHitDto save(EndpointHitDto endpointHitDto) {
return dtoMaper.toDto(repository.save(dtoMaper.fromDto(endpointHitDto)));
    }

    @Override
    public ViewStatsDto getStat(ParamViewStats param) {
        if (param.getUnique()) {
            Long hitCount = repository.getHitCountUnique(param.getStart(), param.getEnd(), param.getUris());
return new ViewStatsDto()
        } else {

        }
    }
}
