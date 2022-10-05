package ru.practicum.models;

import lombok.Data;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
public class ParamViewStats {
    Long start;
    Long end;
    List<String> uris;
    Boolean unique;
    DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public ParamViewStats(String start, String end, String[] uris, Boolean unique) {
        this.start = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8), formatter)
                .toEpochSecond(ZoneOffset.UTC);
        this.end = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8), formatter)
                .toEpochSecond(ZoneOffset.UTC);
        this.uris = Arrays.asList(uris);
        this.unique = unique;
    }
}
