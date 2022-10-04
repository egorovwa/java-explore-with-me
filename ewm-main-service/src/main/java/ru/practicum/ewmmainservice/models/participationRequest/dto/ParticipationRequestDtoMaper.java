package ru.practicum.ewmmainservice.models.participationRequest.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class ParticipationRequestDtoMaper {
    private final DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(formatter.format(LocalDateTime.ofEpochSecond(request.getCreated(), 0,
                ZoneOffset.UTC)), request.getEvent().getId(), request.getId(),
                request.getRequester().getId(), request.getStatus());
    }
}
