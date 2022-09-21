package ru.practicum.ewmmainservice.models.location.dto;

import ru.practicum.ewmmainservice.models.location.Location;

public class LocationDtoMaper {
   public LocationDto toDto(Location location){
       return new LocationDto(location.getLat(), location.getLon());
   }

    public Location fromDto(LocationDto locationDto) {
        return new Location(null, locationDto.lat, locationDto.lon);
    }
}
