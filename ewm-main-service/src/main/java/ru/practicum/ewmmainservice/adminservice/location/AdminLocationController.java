package ru.practicum.ewmmainservice.adminservice.location;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.location.Coordinates;
import ru.practicum.ewmmainservice.models.location.dto.LocationFullDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationShortDto;
import ru.practicum.ewmmainservice.models.location.dto.NewLocationDto;
import ru.practicum.ewmmainservice.utils.PageParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/locations")
@Validated
public class AdminLocationController {
    private final AdminLocationService service;

    @PostMapping
    public LocationFullDto createLocation(@Valid @RequestBody NewLocationDto newLocationDto) throws NotFoundException, LocationException, ModelAlreadyExistsException {
        return service.adminCreateLocation(newLocationDto);
    }

    @GetMapping
    public Collection<LocationShortDto> findAll(@RequestParam(value = "approved", required = false) Boolean approved,
                                                @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                                @Positive @RequestParam(value = "size", defaultValue = "10") int size) throws IncorrectPageValueException {
        Pageable pageable = PageParam.createPageable(from, size);
        return service.findAll(approved,pageable);
    }

    @GetMapping("/{locId}")
    public LocationFullDto findById(@PathVariable("locId") Long locId) throws NotFoundException {
        return service.findById(locId);
    }

    @GetMapping("/{locId}/childs")
    public Collection<Coordinates> findChilds(@RequestParam(value = "withNested", defaultValue = "false") Boolean withNested,
                                              @PathVariable("locId") Long locId) throws NotFoundException {
        return service.findChilds(locId, withNested);
    }
    @PatchMapping("/{locId}/approved")
    public LocationFullDto approvedLocation(@PathVariable("locId") Long locId) throws NotRequiredException, NotFoundException {
       return service.approvedLocation(locId);
    }
    @DeleteMapping("/{locId}/reject")
    public void rejectLocation(@PathVariable("locId") Long locId) throws NotRequiredException, NotFoundException {
        service.rejectLocation(locId);
    }

}
