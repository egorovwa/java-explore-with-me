package ru.practicum.ewmmainservice.models.compilation.dto;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.event.EventShortDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationDtoMaper {
    private final EventDtoMaper eventDtoMaper;

    public CompilationDto toDto(Compilation compilation) {
        Collection<EventShortDto> eventShortDtos = compilation.getEvents().stream()
                .map(eventDtoMaper::toShortDto).collect(Collectors.toList());
        return new CompilationDto(eventShortDtos, compilation.getId(), compilation.getPinned(), compilation.getTitle());
    }
}
