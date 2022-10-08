package ru.practicum.ewmmainservice.models.location.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.location.Location;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class LocationDtoMaper {
    public LocationFullDto toFullDto(Location location) {
        Long parentId;
        if (location.getParent()!=null){
            parentId = location.getParent().getId();
        }else parentId = null;
        return new LocationFullDto(location.getId(), location.getName(), location.getLat(), location.getLon(),
                location.getRadius(), parentId,
                location.getChilds().stream().map(this::toFullDto).collect(Collectors.toList())); // TODO: 06.10.2022 shortDto
    }

    public Location fromDto(LocationFullDto locationFullDto) {
        return null;//new Location(null, locationFullDto.getLat(), locationFullDto.getLon());
    }

    public Location fromNewDto(NewLocationDto newLocationDto, Location parent) {
        int radius;
        if (newLocationDto.getRadius() != null){
            radius = newLocationDto.getRadius();
        } else radius = 10;
        return new Location(newLocationDto.getName(), newLocationDto.getLat(), newLocationDto.getLon(),
                radius, parent, false);
    }
    public LocationShortDto toShortDto(Location location){
        Long parentId = null;
        if (location.getParent() != null){
            parentId = location.getParent().getId();
        }
        Collection<Long> childsId = location.getChilds().stream()
                .map(Location::getId).collect(Collectors.toList());
        return new LocationShortDto(location.getId(), location.getName(), location.getLat(), location.getLon(), location.getRadius(),
                parentId,location.getApproved(), childsId);
    }
}
