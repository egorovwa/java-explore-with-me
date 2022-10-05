package ru.practicum.ewmmainservice.models.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @NotNull
    Float lat;
    @NotNull
    Float lon;
}
