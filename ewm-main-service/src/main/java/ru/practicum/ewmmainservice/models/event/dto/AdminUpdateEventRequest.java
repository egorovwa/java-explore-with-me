package ru.practicum.ewmmainservice.models.event.dto;

import lombok.Data;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;

@Data
public class AdminUpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}