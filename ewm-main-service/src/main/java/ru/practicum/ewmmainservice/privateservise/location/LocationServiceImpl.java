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
    public Location findLocation(Long locationId) throws NotFoundException {
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Id", locationId.toString(), "Location"));
    }

    @Override
    public Collection<LocationShortDto> fidLocations(Long userId, Long locId, Pageable pageable) {
        if (locId == null) {
            return locationRepository.findAll(pageable).map(locationDtoMaper::toShortDto).toList();
        } else {
            return locationRepository.findAllByParentId(locId, pageable)
                    .map(locationDtoMaper::toShortDto).toList();
        }
    }

    @Override
    public LocationFullDto createLocation(NewLocationDto newLocationDto, Long userId) throws NotFoundException, ModelAlreadyExistsException, LocationException {
        userAdminService.findById(userId);
        Location location = adminLocationService.createLocation(newLocationDto);
        location.setApproved(false);
        return locationDtoMaper.toFullDto(location);
    }


}
