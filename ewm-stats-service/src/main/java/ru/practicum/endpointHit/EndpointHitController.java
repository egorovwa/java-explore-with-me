package ru.practicum.endpointHit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStatsDto;
import ru.practicum.models.ParamViewStats;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EndpointHitController {
    private final EndPointHitService service;

    @PostMapping("/hit")
    public EndpointHitDto createHit(@RequestBody EndpointHitDto endpointHitDto) {
        service.save(endpointHitDto);
        return endpointHitDto;
    }

    @GetMapping("/stats")
    public ViewStatsDto getStat(@RequestParam("start") String start,
                                @RequestParam("end") String end,
                                @RequestParam("uris") String[] uris,
                                @RequestParam("unique") Boolean unique) {
        ParamViewStats param = new ParamViewStats(start, end, uris, unique);

        return service.getStat(param);
    }
}
