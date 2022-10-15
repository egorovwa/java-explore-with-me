package ru.practicum.ewmmainservice.adminservice.location;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotRequiredException;
import ru.practicum.ewmmainservice.models.location.Coordinates;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

import java.util.Collection;
import java.util.Optional;

public interface AdminLocationService {
    Location createLocation(NewLocationDto newLocationDto, Boolean approved) throws NotFoundException, LocationException, ModelAlreadyExistsException;

    LocationFullDto adminCreateLocation(NewLocationDto newLocationDto) throws ModelAlreadyExistsException, NotFoundException, LocationException;

    Collection<LocationShortDto> findAll(Boolean approved, Pageable pageable);

    LocationFullDto findById(Long locId) throws NotFoundException;

    Collection<Coordinates> findChilds(Long locId, Boolean withNested) throws NotFoundException;

    Collection<Location> findChilds(Location location);
    Optional<Location> findParrent(NewLocationDto coordinates);

    LocationFullDto approvedLocation(Long locId) throws NotFoundException, NotRequiredException;

    void rejectLocation(Long locId) throws NotFoundException, NotRequiredException;
}
