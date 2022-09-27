package ru.practicum.ewmmainservice.publicService.event;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class PublicEventController {
    private final PublicEventService service;
    @GetMapping
    public Collection<EventShortDto> findEfents(){
        return service.findEfents();
    }
}
