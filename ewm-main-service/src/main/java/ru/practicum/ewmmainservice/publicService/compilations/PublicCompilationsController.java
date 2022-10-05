package ru.practicum.ewmmainservice.publicService.compilations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.compilation.dto.CompilationDto;
import ru.practicum.ewmmainservice.utils.PageParam;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
@Validated
public class PublicCompilationsController {
    private final PublicCompilationsService service;

    @GetMapping
    public List<CompilationDto> findCompilations(@RequestParam(value = "pinned", defaultValue = "false") Boolean pinned,
                                                 @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                                 @Positive @RequestParam(value = "size", defaultValue = "10") int size) throws IncorrectPageValueException {
        Pageable pageable = PageParam.createPageable(from, size);
        return service.findCompilations(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public CompilationDto findCompilation(@Positive @PathVariable("compId") Long compId) throws NotFoundException {
        return service.findCompilation(compId);
    }
}
