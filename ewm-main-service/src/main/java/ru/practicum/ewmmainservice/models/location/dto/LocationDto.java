package ru.practicum.ewmmainservice.models.location.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class LocationDto {
    @NotNull
    Float lat;
    @NotNull
    Float lon;
}
