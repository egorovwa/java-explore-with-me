package ru.practicum.ewmmainservice.models.parameters;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class ParametersPublicEventFind {
    String text;
    List<Long> catIds;
    Boolean paid;
    Long rangeStart;
    Long rangeEnd;
    Boolean onlyAvailable;
    Pageable pageable;
    String clientIp;
    String endpointPath;
    DateTimeFormatter formatter = Utils.getDateTimeFormater();

    public ParametersPublicEventFind(String text, Long[] catIds, Boolean paid, String rangeStart,
                                     String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size ,
                                     String clientIp, String endpointPath) throws IncorrectPageValueException {
        this.text = text;
        this.catIds = Arrays.asList(catIds);
        this.paid = paid;
        this.rangeStart = LocalDateTime.parse(rangeStart, formatter).toEpochSecond(ZoneOffset.UTC);
        this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter).toEpochSecond(ZoneOffset.UTC);
        this.onlyAvailable = onlyAvailable;
        this.clientIp = clientIp;
        this.endpointPath = endpointPath;
        if (sort.equals("EVENT_DATE")){
            this.pageable = PageParam.createPageable(from, size,"event_date");
        }else if (sort.equals("VIEWS")){
            this.pageable = PageParam.createPageable(from, size, "views");
        }else {
            throw new IllegalArgumentException(String.format("Sort by %s not found", sort));
        }
    }
}
