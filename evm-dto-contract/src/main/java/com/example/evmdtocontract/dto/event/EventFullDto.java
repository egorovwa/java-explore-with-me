package com.example.evmdtocontract.dto.event;

import com.example.evmdtocontract.dto.category.CategoryDto;
import com.example.evmdtocontract.dto.location.LocationDto;
import com.example.evmdtocontract.dto.user.UserShortDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFullDto {
    private Long id;
    @Size(max = 2000, min = 20)
    @NotNull
    private String annotation;
    @NotNull
    private CategoryDto category;
    private Integer confirmedRequests; // одобренные участники кол-во
    @NotNull
    private String createdOn;
    @Size(max = 7000, min = 20)
    private String description;
    @NotNull
    private String eventDate;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    private int participantLimit;

    private String publishedOn;
    private Boolean requestModeration;
    @NotNull
    private EventState state;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    private int views;
}
