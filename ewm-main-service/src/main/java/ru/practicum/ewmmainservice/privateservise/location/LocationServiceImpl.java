package ru.practicum.ewmmainservice.privateservise.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationDtoMaper locationDtoMaper;

    @Override
    public Optional<Location> findByLatAndLon(Float lat, Float lon) {
        log.debug("Find lacation with lat = {} lon = {}", lat, lon);
        return locationRepository.findByLatAndLon(lat, lon);
    }

    @Override
    public Location save(LocationDto locationDto) {
        log.info("Create locationwith lat = {} lon = {}", locationDto.getLat(), locationDto.getLon());
        return locationRepository.save(locationDtoMaper.fromDto(locationDto));
    }
}
