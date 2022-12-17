package ru.practicum.ewmmainservice.adminservice.compilation;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.compilation.NewCompilationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotRequiredException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
@Validated
public class CompilationController {
    private final CompilationService service;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilationDto) throws FiledParamNotFoundException {
        return service.createCompilation(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@Positive @PathVariable("compId") Long compId) throws NotFoundException {
        service.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@Positive @PathVariable("compId") Long compId,
                                           @Positive @PathVariable("eventId") Long eventId) throws NotFoundException, FiledParamNotFoundException {
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventToCompilation(@Positive @PathVariable("compId") Long compId,
                                      @Positive @PathVariable("eventId") Long eventId) throws NotRequiredException, NotFoundException {
        service.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void deletePinned(@Positive @PathVariable("compId") Long compId) throws NotRequiredException, NotFoundException {
        service.deletePinned(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addPinned(@Positive @PathVariable("compId") Long compId) throws NotRequiredException, NotFoundException {
        service.addPinned(compId);
    }
}
