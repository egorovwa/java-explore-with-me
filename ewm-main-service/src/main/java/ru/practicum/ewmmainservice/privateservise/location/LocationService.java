package ru.practicum.ewmmainservice.privateservise.location;

import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;

import java.util.Optional;

public interface LocationService {
    Optional<Location> findByLatAndLon(double lat, double lon);

    Location save(LocationFullDto locationFullDto);
}
