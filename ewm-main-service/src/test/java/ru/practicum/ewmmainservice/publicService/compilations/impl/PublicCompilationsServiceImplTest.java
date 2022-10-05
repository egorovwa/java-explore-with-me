package ru.practicum.ewmmainservice.publicService.compilations.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationRepository;
import ru.practicum.ewmmainservice.adminService.compilation.CompilationService;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.compilation.Compilation;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDtoMaper;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.utils.PageParam;

import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PublicCompilationsServiceImplTest {
    @InjectMocks
    PublicCompilationsServiceImpl service;
    @Mock
    private CompilationRepository repository;
    @Mock
    private CompilationDtoMaper dtoMaper;
    @Mock
    private CompilationService adminService;

    @Test
    void findCompilations() throws IncorrectPageValueException {
        Compilation compilation = new Compilation(List.of(new Event()), 1L, true, "title");
        Page<Compilation> page = new PageImpl<>(List.of(compilation));
        when(repository.findAllByPinned(true, PageParam.createPageable(1, 10)))
                .thenReturn(page);
        service.findCompilations(true, PageParam.createPageable(1, 10));
        verify(repository, times(1)).findAllByPinned(true, PageParam.createPageable(1, 10));

    }

    @Test
    void findCompilation() throws NotFoundException {
        Compilation compilation = new Compilation(List.of(new Event()), 1L, true, "title");

        when(adminService.findById(1L))
                .thenReturn(compilation);
        service.findCompilation(1L);
        verify(adminService, times(1)).findById(1L);
    }
}