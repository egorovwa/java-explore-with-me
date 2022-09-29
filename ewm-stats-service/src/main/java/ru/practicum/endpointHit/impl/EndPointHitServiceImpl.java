package ru.practicum.endpointHit.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.endpointHit.EndPointHitService;
import ru.practicum.endpointHit.EndpointHitRepository;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.models.EndpointHitDtoMaper;

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
}
