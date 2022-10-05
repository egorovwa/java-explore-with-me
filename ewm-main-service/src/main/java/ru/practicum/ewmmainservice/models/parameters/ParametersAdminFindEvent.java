package ru.practicum.ewmmainservice.models.parameters;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.models.event.EventState;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ParametersAdminFindEvent {
    List<Long> users;
    List<EventState> states;
    List<Long> categories;
    Long rangeStart;
    Long rangeEnd;
    Pageable pageable;
    DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public ParametersAdminFindEvent(Long[] users, String[] states, Long[] categories, String rangeStart,
                                    String rangeEnd, Integer from, Integer size) throws IncorrectPageValueException {
        this.users = Arrays.asList(users);
        this.states = Arrays.asList(states).stream().map(EventState::from)
                .map(r -> r.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + r)))
                .collect(Collectors.toList());
        this.categories = Arrays.asList(categories);
        this.rangeStart = LocalDateTime.parse(rangeStart, formatter).toEpochSecond(ZoneOffset.UTC);
        this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter).toEpochSecond(ZoneOffset.UTC);
        this.pageable = PageParam.createPageable(from, size);
    }
}
