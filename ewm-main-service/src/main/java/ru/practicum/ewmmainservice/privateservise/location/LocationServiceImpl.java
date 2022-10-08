package ru.practicum.ewmmainservice.privateservise.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDtoMaper locationDtoMaper;

    @Override
    public Optional<Location> findByLatAndLon(double lat, double lon) {
        log.debug("Find lacation with lat = {} lon = {}", lat, lon);
        return locationRepository.findByLatAndLon(lat, lon);
    }

    @Override
    @Transactional
    public Location save(LocationFullDto locationFullDto) {
        log.info("Create locationwith lat = {} lon = {}", locationFullDto.getLat(), locationFullDto.getLon());
        return locationRepository.save(locationDtoMaper.fromDto(locationFullDto));
    }
}
