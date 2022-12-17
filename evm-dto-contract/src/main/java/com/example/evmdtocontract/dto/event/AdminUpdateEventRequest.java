package com.example.evmdtocontract.dto.event;

import com.example.evmdtocontract.dto.location.LocationDto;
import lombok.Data;


@Data
public class AdminUpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}