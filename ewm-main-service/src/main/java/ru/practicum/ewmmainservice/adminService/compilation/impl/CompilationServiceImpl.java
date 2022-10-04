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
import ru.practicum.ewmmainservice.exceptions.RuntimeNotFoundException;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.models.compilation.dto.NewCompilationDto;
import ru.practicum.ewmmainservice.models.event.Event;

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
                            throw new RuntimeNotFoundException("id", r.toString(), "Event");
                        }
                    })
                    .collect(Collectors.toUnmodifiableList());
        } catch (RuntimeNotFoundException e) {
            throw new FiledParamNotFoundException(String.format("Parameter %s %s = %s not found", e.getClassName(),
                    e.getParam(), e.getValue()));
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
    public Compilation findById(Long compId) throws NotFoundException {
        return repository.findById(compId).orElseThrow(() -> new NotFoundException("id", compId.toString(), "Compilation")
        );
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) throws NotFoundException, FiledParamNotFoundException {
        Compilation compilation = findById(compId);
        Event toDelete = compilation.getEvents().stream().
                filter(r -> r.getId().equals(eventId)).findFirst()
                .orElseThrow(() -> new FiledParamNotFoundException(String.format("Event id = %s not found in compilation id = %s", eventId,
                        compId)));
        compilation.getEvents().removeIf(r -> r.getId() == eventId);
        repository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        Event event = eventService.findById(eventId);
        if (!compilation.getEvents().contains(event)) {

            compilation.getEvents().add(event);
            repository.save(compilation);
        } else {
            throw new NotRequiredException(String.format("Even id = %s aredy exist in Compilation id = %s",
                    eventId, compId));
        }
    }

    @Override
    public void deletePinned(Long compId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        if (compilation.getPinned()) {
            compilation.setPinned(false);
            repository.save(compilation);
        } else {
            throw new NotRequiredException("Compilation not pinned");
        }
    }

    @Override
    public void addPinned(Long compId) throws NotFoundException, NotRequiredException {
        Compilation compilation = findById(compId);
        if (!compilation.getPinned()) {
            compilation.setPinned(true);
            repository.save(compilation);
        } else {
            throw new NotRequiredException("Compilation already pinned");
        }
    }
}
