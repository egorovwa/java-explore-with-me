package ru.practicum.ewmmainservice.publicservice.compilations;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;

import java.util.List;

public interface PublicCompilationsService {
    List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable);

    CompilationDto findCompilation(Long compId) throws NotFoundException;
}
