package ru.practicum.ewmmainservice.adminservice.location;

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

public interface AdminLocationService {
    LocationFullDto createLocation(NewLocationDto newLocationDto) throws NotFoundException, LocationException, ModelAlreadyExistsException;

    Collection<LocationShortDto> findAll(Pageable pageable);

    LocationFullDto findById(Long locId) throws NotFoundException;

    Collection<Coordinates> findChilds(Long locId, Boolean withNested) throws NotFoundException;

    Collection<Location> findChilds(Location location);
}
