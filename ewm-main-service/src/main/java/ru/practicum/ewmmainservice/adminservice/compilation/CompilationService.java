package ru.practicum.ewmmainservice.adminservice.compilation;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import com.example.evmdtocontract.dto.compilation.NewCompilationDto;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.exceptions.NotRequiredException;
import ru.practicum.ewmmainservice.models.compilation.Compilation;


public interface CompilationService {
    CompilationDto createCompilation(NewCompilationDto newCompilationDto) throws FiledParamNotFoundException;

    void deleteCompilation(Long compId) throws NotFoundException;

    Compilation findById(Long compId) throws NotFoundException;

    void deleteEventFromCompilation(Long compId, Long eventId) throws NotFoundException, FiledParamNotFoundException;

    void addEventToCompilation(Long compId, Long eventId) throws NotFoundException, NotRequiredException;

    void deletePinned(Long compId) throws NotFoundException, NotRequiredException;

    void addPinned(Long compId) throws NotFoundException, NotRequiredException;
}
