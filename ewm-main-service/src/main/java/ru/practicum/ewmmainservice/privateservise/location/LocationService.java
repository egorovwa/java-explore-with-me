package ru.practicum.ewmmainservice.privateservise.location;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

import java.util.Collection;

public interface LocationService {
    Location findLocationById(Long location) throws NotFoundException;

    Collection<LocationShortDto> fidLocations(Long locId, Pageable pageable);

    LocationFullDto createLocation(NewLocationDto newLocationDto, Long userId) throws NotFoundException, ModelAlreadyExistsException, LocationException;

    LocationFullDto findLocationDtoById(Long locId) throws NotFoundException;
}
