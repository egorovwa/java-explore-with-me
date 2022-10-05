package ru.practicum.ewmmainservice.models.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    Collection<Long> events;
    @NonNull
    Boolean pinned;
    @NotBlank
    String title;
}
