package ru.practicum.ewmmainservice.privateservise.location;

import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;

import java.util.Optional;

public interface LocationService {
    Optional<Location> findByLatAndLon(Float lat, Float lon);

    Location save(LocationDto locationDto);
}
