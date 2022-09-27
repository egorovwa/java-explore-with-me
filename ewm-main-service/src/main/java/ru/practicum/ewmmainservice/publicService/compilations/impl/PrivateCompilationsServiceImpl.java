package ru.practicum.ewmmainservice.publicService.compilations.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationRepository;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationService;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.publicService.compilations.PrivateCompilationsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateCompilationsServiceImpl implements PrivateCompilationsService {
    private final CompilationRepository repository;
    private final CompilationDtoMaper dtoMaper;
    private final CompilationService adminService;

    @Override
    public List<CompilationDto> findCompilations(Boolean pinned, Pageable pageable) {

        return repository.findAllByPinned(pinned, pageable).map(dtoMaper::toDto).toList();
    }

    @Override
    public CompilationDto findCompilation(Long compId) throws NotFoundException {
        return dtoMaper.toDto(adminService.findById(compId));
    }
}
