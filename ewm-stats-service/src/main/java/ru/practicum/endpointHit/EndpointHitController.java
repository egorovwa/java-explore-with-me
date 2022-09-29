package ru.practicum.endpointHit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.dto.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    public ViewStats getStat(HttpServletRequest request,
                             @RequestParam("start") String start,
                             @RequestParam("end") String end,
                             @RequestParam("uris") String[] uris,
                             @RequestParam("unique") Boolean unique) {
        log.info("URI LLLLLLLLLLLLLLLL {}",request.getQueryString());
log.info(URLDecoder.decode(start, StandardCharsets.UTF_8));
log.info(URLDecoder.decode(end, StandardCharsets.UTF_8));
        return new ViewStats(); //service.getStat();
    }
}
