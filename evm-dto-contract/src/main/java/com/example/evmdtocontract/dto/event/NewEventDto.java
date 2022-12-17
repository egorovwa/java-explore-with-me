package com.example.evmdtocontract.dto.event;

import com.example.evmdtocontract.dto.location.LocationDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class NewEventDto {
    @Size(min = 20, max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @Size(min = 20, max = 7000)
    private String description;
    @DateTimeFormat
    private String eventDate;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    private int participantLimit = 0;
    private Boolean requestModeration;
    @Size(min = 3, max = 120)
    private String title;
}
