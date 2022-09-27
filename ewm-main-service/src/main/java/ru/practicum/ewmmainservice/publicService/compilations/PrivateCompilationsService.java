package ru.practicum.ewmmainservice.publicService.compilations;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDto;

import java.util.List;

public interface PrivateCompilationsService {
    List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable);

    CompilationDto findCompilation(Long compId) throws NotFoundException;
}
