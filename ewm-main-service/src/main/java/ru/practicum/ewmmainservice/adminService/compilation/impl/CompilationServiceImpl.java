package ru.practicum.ewmmainservice.adminService.compilation.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationRepository;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationService;
import ru.practicum.ewmmainservice.adminService.event.AdminEventService;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotRequiredException;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.models.compilation.dto.NewCompilationDto;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final AdminEventService eventService;
    private final CompilationDtoMaper dtoMaper;

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) throws FiledParamNotFoundException {
        List<Event> events;
        try {
            events = newCompilationDto.getEvents().stream()
                    .map(r -> {
                        try {
                            return eventService.findById(r);
                        } catch (NotFoundException e) {
                            log.warn("Compilation id = {} not found", r);
                            throw new RuntimeException(e); // TODO: 25.09.2022 кода apiError
                        }
                    })
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new FiledParamNotFoundException("One or more event not found");
        }
        Compilation compilation = new Compilation(events, null, newCompilationDto.getPinned(),
                newCompilationDto.getTitle());
        log.info("Created Compilation {}", compilation);
        return dtoMaper.toDto(repository.save(compilation));
    }


    @Override
    public void deleteCompilation(Long compId) throws NotFoundException {
        Compilation compilation = findById(compId);
        log.info("Compilation id {} deleted", compId);
        repository.deleteById(compId);

    }

    @Override
    public Compilation findById(Long compId) throws NotFoundException { // TODO: 25.09.2022 если не пригодиться private
        return repository.findById(compId).orElseThrow(() -> new NotFoundException(String.
                format("Compilation id = %s not found", compId), "id", compId.toString(), "Compilation")
        );
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) throws NotFoundException, FiledParamNotFoundException {
        Compilation compilation = findById(compId);
        Event toDelete = compilation.getEvents().stream().
                filter(r -> r.getId().equals(eventId)).findFirst()
                .orElseThrow(() -> new FiledParamNotFoundException(String.format("Event id = %s not found in compilation id = %s", eventId,
                        compId)));
        Collection<Event> events = compilation.getEvents().stream()
                .filter(r -> r.getId() != eventId).collect(Collectors.toUnmodifiableList());
        compilation.setEvents(events);
        repository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        if (!compilation.getEvents().contains(event)){
            List<Event> events = new ArrayList<>(compilation.getEvents());
            events.add(event);
            compilation.setEvents(Collections.unmodifiableList(events));
            repository.save(compilation);
        }else {
           throw new NotRequiredException(String.format("Even id = %s aredy exist in Compilation id = %s",
                   eventId, compId));
        }
    }

    @Override
    public void deletePinned(Long compId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        if (compilation.getPinned()){
            compilation.setPinned(false);
            repository.save(compilation);
        } else {
            throw new NotRequiredException("Compilation not pinned");
        }
    }

    @Override
    public void addPinned(Long compId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        if (!compilation.getPinned()){
            compilation.setPinned(true);
            repository.save(compilation);
        } else {
            throw new NotRequiredException("Compilation already pinned");
        }
    }
}
