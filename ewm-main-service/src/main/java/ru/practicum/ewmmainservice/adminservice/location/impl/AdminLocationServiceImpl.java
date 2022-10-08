package ru.practicum.ewmmainservice.adminservice.location.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationRepository;
import ru.practicum.ewmmainservice.adminservice.location.AdminLocationService;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.Coordinates;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;
import ru.practicum.ewmmainservice.utils.PointsOperations;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminLocationServiceImpl implements AdminLocationService {
    private final AdminLocationRepository repository;
    private final LocationDtoMaper dtoMaper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public LocationFullDto createLocation(NewLocationDto newLocationDto) throws NotFoundException, LocationException, ModelAlreadyExistsException {
        Collection<Location> toSave = new ArrayList();
        if (newLocationDto.getParentId() != null) {
            return createWithParent(newLocationDto, toSave);
        } else {
            return createWithOutParent(newLocationDto, toSave);
        }
    }

    private LocationFullDto createWithOutParent(NewLocationDto newLocationDto, Collection<Location> toSave) throws ModelAlreadyExistsException {
        Location location = dtoMaper.fromNewDto(newLocationDto, null);
        location.setApproved(true);
        findParent(location)
                .ifPresent(p -> {
                    p.getChilds().add(location);
                    location.setParent(p);
                });
        Collection<Location> childs = findChilds(location);
        location.setChilds(childs);
        toSave.add(location);
        toSave.addAll(childs);
        try {
            repository.saveAll(toSave);
        }catch (DataIntegrityViolationException e){
            throw new ModelAlreadyExistsException("coordinats",
                    String.format("lat = %s lon = %s radius = %s", location.getLat(),
                            location.getLon(), location.getRadius()), "Location");
        }
        return dtoMaper.toFullDto(location);
    }

    private LocationFullDto createWithParent(NewLocationDto newLocationDto, Collection<Location> toSave) throws NotFoundException, LocationException, ModelAlreadyExistsException {
        Location parent = repository.findById(newLocationDto.getParentId())
                .orElseThrow(() -> new NotFoundException("id", newLocationDto.getParentId().toString(), "Parent location"));
        Long distant = PointsOperations.getDistance(parent, newLocationDto);
        if (distant > parent.getRadius()) {
            throw new LocationException(String.format("Location %s cannot be the parent for location %s, because it is located at a distance of %sm, and the radius of parent is %sm.",
                    parent, newLocationDto, distant, parent.getRadius()), "Error creating a location.");
        }
        Location location = dtoMaper.fromNewDto(newLocationDto, parent);
        location.setApproved(true);
        Collection<Location> childs = findChilds(location);
        location.setChilds(childs);
        parent.getChilds().add(location);
        toSave.add(location);
        toSave.add(parent);
        toSave.addAll(childs);
        try {
            repository.saveAll(toSave);
        }catch (DataIntegrityViolationException e){
            throw new ModelAlreadyExistsException("coordinats",
                    String.format("lat = %s lon = %s radius = %s", location.getLat(),
                            location.getLon(), location.getRadius()), "Location");
        }
        return dtoMaper.toFullDto(location);
    }

    @Override
    public Collection<LocationShortDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(dtoMaper::toShortDto)
                .toList();
    }

    @Override
    public LocationFullDto findById(Long locId) throws NotFoundException {
        return dtoMaper.toFullDto(repository.findById(locId)
                .orElseThrow(()-> new NotFoundException("id", locId.toString(),
                        "Location")));
    }

    @Override
    public Collection<Coordinates> findChilds(Long locId, Boolean withNested) throws NotFoundException {
        Location location = repository.findById(locId)
                .orElseThrow(()-> new NotFoundException("id", locId.toString(),
                        "Location"));
        if (withNested){
            return location.getChilds().stream()
                    .map(dtoMaper::toFullDto)
                    .collect(Collectors.toList());
        }else {
            return location.getChilds().stream()
                    .map(dtoMaper::toShortDto)
                    .collect(Collectors.toList());
        }
    }

    private Optional<Location> findParent(Location location) {
        Collection<Location> parents = repository.findParents(location.getLat(), location.getLon(), location.getRadius());
        return parents.stream().findFirst();
    }
    @Override
    public Collection<Location> findChilds(Location location) {
        double radiusKm = location.getRadius() / 1000.0;
        Collection<Location> childs = repository.findChilds(location.getLat(), location.getLon(), radiusKm);
              childs =  childs.stream()
                .filter(loc -> (loc.getLat() != location.getLat() || loc.getLon() != location.getLon()) &&
                        loc.getRadius() < location.getRadius())
                .collect(Collectors.toList());
        childs.stream().filter(r -> {
                    if (r.getParent() != null) {
                        return r.getParent().getRadius() >= location.getRadius();                //если радиус родительской локации меньше новой то остается.
                    } else return true;
                })
                .forEach(loc -> {
                    loc.setParent(location);
                });
        return childs;
    }
}
