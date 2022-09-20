package ru.practicum.ewmmainservice.models.event.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewmmainservice.models.location.Location;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewEventDto {
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @DateTimeFormat
    LocalDateTime eventDate; // TODO: 19.09.2022 maper
    @NotNull
    Location location;
    @NotNull
    Boolean paid;
    int participantLimit; // TODO: 19.09.2022 Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Boolean requestModeration; // TODO: 19.09.2022 Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
   @Size(min = 3, max = 120)
    String title;
}
