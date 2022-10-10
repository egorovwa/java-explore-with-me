package ru.practicum.ewmmainservice.privateservise.location;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.Coordinates;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

import java.util.Collection;
import java.util.Optional;

public interface LocationService {
    Location findLocation(Long location) throws NotFoundException;

    Collection<LocationShortDto> fidLocations(Long userId, Long locId, Pageable pageable);

    LocationFullDto createLocation(NewLocationDto newLocationDto, Long userId) throws NotFoundException, ModelAlreadyExistsException, LocationException;
}
