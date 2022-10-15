package ru.practicum.ewmmainservice.models.parameters;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
public class ParametersPublicEventFind {
    private String text;
    private List<Long> catIds;
    private List<Long> locIds;
    private Boolean paid;
    private Long rangeStart;
    private Long rangeEnd;
    private Boolean onlyAvailable;
    private Pageable pageable;
    private String clientIp;
    private String endpointPath;
    private boolean withChilds;
    private DateTimeFormatter formatter = Utils.getDateTimeFormatter();

    public ParametersPublicEventFind(String text, Long[] catIds, Long[] locIds, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size,
                                     String clientIp, String endpointPath, Boolean withChilds) throws IncorrectPageValueException, IllegalTimeException {
        this.text = text;
        if (catIds != null) {
            this.catIds = Arrays.asList(catIds);
        }
        if (locIds != null) {
            this.locIds = Arrays.asList(locIds);
        }
        this.paid = paid;

        this.rangeStart = LocalDateTime.parse(rangeStart, formatter).toEpochSecond(ZoneOffset.UTC);
        this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter).toEpochSecond(ZoneOffset.UTC);
        if (this.rangeStart >= this.rangeEnd) {
            throw new IllegalTimeException(String.format("The start (%s) should be greater than the end (%s).",
                    formatter.format(LocalDateTime.parse(rangeStart, formatter)),
                    formatter.format(LocalDateTime.parse(rangeEnd, formatter))), "");
        }
        this.onlyAvailable = onlyAvailable;
        this.clientIp = clientIp;
        this.endpointPath = endpointPath;
        if (sort.equals("EVENT_DATE")) {
            this.pageable = PageParam.createPageable(from, size, "eventDate");
        } else if (sort.equals("VIEWS")) {
            this.pageable = PageParam.createPageable(from, size, "views");
        } else {
            throw new IllegalArgumentException(String.format("Sort by %s not found", sort));
        }
        if (locIds != null) {
            this.withChilds = withChilds;
        }
    }
}
