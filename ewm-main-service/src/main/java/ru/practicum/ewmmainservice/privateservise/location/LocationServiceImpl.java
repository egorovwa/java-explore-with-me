package ru.practicum.ewmmainservice.privateservise.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationService;
import ru.practicum.ewmmainservice.adminservice.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final AdminLocationService adminLocationService;
    private final LocationDtoMaper locationDtoMaper;
    private final UserAdminService userAdminService;


    @Override
    public Location findLocationById(Long locationId) throws NotFoundException {
        log.info("Find location by id = {}", locationId);
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Id", locationId.toString(), "Location"));
    }

    @Override
    public Collection<LocationShortDto> fidLocations(Long locId, Pageable pageable) {
        if (locId == null) {
            log.info("Find all location");
            return locationRepository.findAll(pageable).map(locationDtoMaper::toShortDto).toList();
        } else {
            log.info("Find all location in location id = {}", locId);
            return locationRepository.findAllByParentId(locId, pageable)
                    .map(locationDtoMaper::toShortDto).toList();
        }
    }

    @Override
    public LocationFullDto createLocation(NewLocationDto newLocationDto, Long userId) throws NotFoundException, ModelAlreadyExistsException, LocationException {
        userAdminService.findById(userId);
        Location location = adminLocationService.createLocation(newLocationDto,false);

        log.info("User id = {} create location {}", userId, location);
        return locationDtoMaper.toFullDto(location);
    }

    @Override
    public LocationFullDto findLocationDtoById(Long locId) throws NotFoundException {
        return locationDtoMaper.toFullDto(findLocationById(locId));
    }


}
