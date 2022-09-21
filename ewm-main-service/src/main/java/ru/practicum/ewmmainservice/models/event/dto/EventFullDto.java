package ru.practicum.ewmmainservice.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
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
    private String createdOn; // TODO: 21.09.2022 formater in maper "yyyy-MM-dd HH:mm:ss"
    @Size(max = 7000, min = 20)
    private String description;
    @NotNull
    private String eventDate; // TODO: 21.09.2022 formater in maper "yyyy-MM-dd HH:mm:ss"
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid;
    private int participantLimit;

    private String publishedOn; // TODO: 21.09.2022 formater in maper "yyyy-MM-dd HH:mm:ss"
    private Boolean requestModeration;
    @NotNull
    private EventState state;
    @NotNull
    @Size(min = 3, max = 120)
    private String title;
    private int views;
}
