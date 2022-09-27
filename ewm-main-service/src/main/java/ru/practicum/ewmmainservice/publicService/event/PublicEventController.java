package ru.practicum.ewmmainservice.publicService.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@Slf4j
public class PublicEventController {
    private final PublicEventService service;
    @GetMapping
    public Collection<EventShortDto> findEfents(HttpServletRequest request,
                                                @RequestParam("text") String text,
                                                @RequestParam("categories") Long[] categories,
                                                @RequestParam("paid") Boolean paid,
                                                @RequestParam("rangeStart") String rangeStart,
                                                @RequestParam("rangeEnd") String rangeEnd,
                                                @RequestParam("onlyAvailable") Boolean onlyAvailable,
                                                @RequestParam("sort") String sort,
                                                @RequestParam(value = "from",defaultValue = "0") int from,
                                                @RequestParam(value = "size", defaultValue = "10") int size) throws IncorrectPageValueException {
        String clientIp = request.getRemoteAddr();
       String endpointPath= request.getRequestURI();
        ParametersPublicEventFind param = new ParametersPublicEventFind(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size, clientIp, endpointPath);
        return service.findEfents(param);
    }
}
