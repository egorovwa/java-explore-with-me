package ru.practicum.endpointHit;

import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStatsDto;
import ru.practicum.models.ParamViewStats;

public interface EndPointHitService {
    EndpointHitDto save(EndpointHitDto endpointHitDto);

    ViewStatsDto getStat(ParamViewStats param);
}
