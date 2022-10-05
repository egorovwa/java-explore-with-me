package ru.practicum.endpointHit;

import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStatsDto;
import ru.practicum.models.ParamViewStats;

import java.util.Collection;

public interface EndPointHitService {
    EndpointHitDto save(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStat(ParamViewStats param);
}
