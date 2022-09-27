package ru.practicum.ewmmainservice.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    Collection<EventShortDto> events;
    Long id;
    @NonNull
    Boolean pinned;
    @NonNull
    @NotBlank
    String title;
}
