package ru.practicum.ewmmainservice.models.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.user.dto.UserShortDto;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private Long id;
    @NotNull
    @Size(max = 2000, min = 20)
    private String annotation;
    @NotNull
    @ManyToOne
    private Category category;
    @NotNull
    private String eventDate;
    private int confirmedRequests;
    @NotNull
    private UserShortDto initiator;
    @NotNull
    private Boolean paid;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    private int views;
}
