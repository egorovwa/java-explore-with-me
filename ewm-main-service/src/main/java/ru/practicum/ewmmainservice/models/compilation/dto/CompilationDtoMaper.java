package ru.practicum.ewmmainservice.models.compilation.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationDtoMaper {
    private final EventDtoMaper eventDtoMaper;
    public CompilationDto toDto(Compilation compilation){
        Collection<EventShortDto> eventShortDtos = compilation.getEvents().stream()
                .map(eventDtoMaper::toShortDto).collect(Collectors.toList());
        return new CompilationDto(eventShortDtos, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
