package ru.practicum.ewmmainservice.exceptions;

import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

public class LocationNotFound extends Exception{
    String reason = "The specified location was not found. If the location does not exist, create a request to add a location.";
    NewLocationDto newLocationDto;
    String message;

    public LocationNotFound(NewLocationDto newLocationDto) {
        this.newLocationDto = newLocationDto;
    }
}
