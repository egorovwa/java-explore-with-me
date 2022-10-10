package ru.practicum.ewmmainservice.publicservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class PublicEventController {
    private final PublicEventService service;

    @GetMapping
    public Collection<EventShortDto> findEfents(HttpServletRequest request,
                                                @RequestParam("text") String text,
                                                @RequestParam(value = "categories", required = false) Long[] categories,
                                                @RequestParam(value = "locIds", required = false) Long[] locIds,
                                                @RequestParam(value = "paid", required = false) Boolean paid,
                                                @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                                @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                                @RequestParam("onlyAvailable") Boolean onlyAvailable,
                                                @RequestParam(value = "withChilds",required = false) Boolean withChilds,
                                                @RequestParam("sort") String sort,
                                                @RequestParam(value = "from", defaultValue = "0") int from,
                                                @RequestParam(value = "size", defaultValue = "10") int size) throws IncorrectPageValueException, IllegalTimeException {
        String clientIp = request.getRemoteAddr();
        String endpointPath = request.getRequestURI();
        ParametersPublicEventFind param = new ParametersPublicEventFind(text, categories, locIds, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, clientIp, endpointPath, withChilds);
        return service.findEvents(param);
    }

    @GetMapping("/{id}")
    public EventFullDto findById(HttpServletRequest request,
                                 @Positive @PathVariable("id") Long id) throws NotFoundException {
        return service.findById(id, request.getRequestURI(), request.getRemoteAddr());
    }
}
