package ru.practicum.ewmmainservice.models.event.dto;

import lombok.Data;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;

@Data
public class AdminUpdateEventRequest {
    String annotation;
    Long category;
    String description;
    String eventDate;
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    String title;
}