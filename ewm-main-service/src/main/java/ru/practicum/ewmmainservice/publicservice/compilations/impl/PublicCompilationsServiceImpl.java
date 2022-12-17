package ru.practicum.ewmmainservice.publicservice.compilations.impl;

import com.example.evmdtocontract.dto.compilation.CompilationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminservice.compilation.CompilationRepository;
import ru.practicum.ewmmainservice.adminservice.compilation.CompilationService;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.publicservice.compilations.PublicCompilationsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationsServiceImpl implements PublicCompilationsService {
    private final CompilationRepository repository;
    private final CompilationDtoMaper dtoMaper;
    private final CompilationService adminService;

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable) {
log.info("Find compilations with pinned = {}", pinned);
        return repository.findAllByPinned(pinned, pageable).map(dtoMaper::toDto).toList();
    }

    @Override
    public CompilationDto findCompilation(Long compId) throws NotFoundException {
        log.info("Find compilation id ={}", compId);
        return dtoMaper.toDto(adminService.findById(compId));
    }
}
