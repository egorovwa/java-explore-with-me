package ru.practicum.models;

import org.springframework.stereotype.Component;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStats;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component

public class EndpointHitDtoMaper {
    private final DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                formatter.format(LocalDateTime.ofEpochSecond(endpointHit.getTimestamp(), 0, ZoneOffset.UTC)));
    }

    public EndpointHit toDto(EndpointHitDto dto) {
        return new EndpointHit(dto.getId(), dto.getApp(), dto.getUri(), dto.getIp(),
                LocalDateTime.parse(dto.getTimestamp(), formatter).toEpochSecond(ZoneOffset.UTC));
    }
}
