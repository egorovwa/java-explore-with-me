package ru.practicum.ewmmainservice.models.location.dto;

import com.example.evmdtocontract.dto.location.LocationDto;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.location.Location;

@Component
public class LocationDtoMaper {
    public LocationDto toDto(Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }

    public Location fromDto(LocationDto locationDto) {
        return new Location(null, locationDto.getLat(), locationDto.getLon());
    }
}
