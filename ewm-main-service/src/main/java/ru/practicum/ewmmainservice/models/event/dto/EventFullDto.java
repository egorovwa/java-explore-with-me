package ru.practicum.ewmmainservice.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.dto.LocationForEventDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.user.dto.UserShortDto;

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
    private Category category;
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
    private LocationForEventDto location;
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
