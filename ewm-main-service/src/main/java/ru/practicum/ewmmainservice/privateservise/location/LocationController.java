package ru.practicum.ewmmainservice.privateservise.location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.LocationException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;
import ru.practicum.ewmmainservice.utils.PageParam;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Validated
public class LocationController {
    private final LocationService service;
    @GetMapping("/{userId}")
    public Collection<LocationShortDto> findLocations(@RequestParam(value = "locId",required = false) Long locId,
                                                      @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                      @RequestParam(value = "size", required = false) Integer size,
                                                      @PathVariable("userId") Long userId) throws IncorrectPageValueException {
        Pageable pageable = PageParam.createPageable(from, size);
        return service.fidLocations(userId, locId, pageable);
    }
    @PostMapping("/{userId}")
    public LocationFullDto createLocation(@Valid @RequestBody NewLocationDto newLocationDto,
                                          @RequestParam("userId") Long userId) throws ModelAlreadyExistsException, NotFoundException, LocationException {
        return service.createLocation(newLocationDto, userId);
    }
}
