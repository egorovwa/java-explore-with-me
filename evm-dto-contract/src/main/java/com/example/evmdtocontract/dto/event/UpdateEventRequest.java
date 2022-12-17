package com.example.evmdtocontract.dto.event;

import lombok.Data;

import javax.validation.constraints.Size;


@Data
public class UpdateEventRequest {
    long eventId;
    @Size(min = 20, max = 2000)
    private String annotation;
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    private String eventDate;
    private Boolean paid;
    private Integer participantLimit;
    @Size(min = 3, max = 120)
    private String title;
}
