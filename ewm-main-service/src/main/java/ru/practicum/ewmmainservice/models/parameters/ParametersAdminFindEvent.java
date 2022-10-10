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
    private List<Long> users;
    private List<EventState> states;
    private List<Long> categories;
    private List<Long> locIds;
    private Long rangeStart;
    private Long rangeEnd;
    private Pageable pageable;
    private DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    public ParametersAdminFindEvent(Long[] users, String[] states, Long[] categories, Long[] locIds, String rangeStart,
                                    String rangeEnd, Integer from, Integer size) throws IncorrectPageValueException {
        this.users = Arrays.asList(users);
        this.states = Arrays.asList(states).stream().map(EventState::from)
                .map(r -> r.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + r)))
                .collect(Collectors.toList());
        this.categories = Arrays.asList(categories);
        this.locIds = Arrays.asList(locIds);
        this.rangeStart = LocalDateTime.parse(rangeStart, formatter).toEpochSecond(ZoneOffset.UTC);
        this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter).toEpochSecond(ZoneOffset.UTC);
        this.pageable = PageParam.createPageable(from, size);
    }
}
