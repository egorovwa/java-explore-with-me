package ru.practicum.ewmmainservice.privateservise.location;

import com.example.evmdtocontract.dto.location.LocationDto;
import ru.practicum.ewmmainservice.models.location.Location;

import java.util.Optional;

public interface LocationService {
    Optional<Location> findByLatAndLon(Float lat, Float lon);

    Location save(LocationDto locationDto);
}
