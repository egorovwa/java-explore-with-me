package ru.practicum.ewmmainservice.publicService.event.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.practicum.ewmmainservice.client.EwmClient;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.IncorrectPageValueException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.utils.PageParam;
import ru.practicum.ewmstatscontract.dto.EndpointHitDto;
import ru.practicum.ewmstatscontract.utils.Utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicEventServiceImplTest {
    final DateTimeFormatter formatter = Utils.getDateTimeFormater();
    @InjectMocks
    PublicEventServiceImpl service;
    @Mock
    private EwmClient client;
    @Mock
    private PrivateEventRepository repository;
    @Mock
    private EventDtoMaper dtoMaper;

    @Test
    void test1_findEvents_whenSortViewAvalibleTrue() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        Page<Event> eventPage = new PageImpl<>(List.of(new Event()));
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true,
                "2022-09-09 00:00:00", "2022-09-10 00:00:00", true, "VIEWS", 0, 10,
                "ip", "/events");
        when(repository.findAllForPublicAvailable("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "views")))
                .thenReturn(eventPage);
        service.findEvents(param);
        verify(repository, times(1)).findAllForPublicAvailable("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "views"));

    }

    @Test
    void test1_2findEvents_whenSortEventDateAvalibleTrue() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        Page<Event> eventPage = new PageImpl<>(List.of(new Event()));
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true,
                "2022-09-09 00:00:00", "2022-09-10 00:00:00", true, "EVENT_DATE", 0, 10,
                "ip", "/events");
        when(repository.findAllForPublicAvailable("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "eventDate")))
                .thenReturn(eventPage);
        service.findEvents(param);
        verify(repository, times(1)).findAllForPublicAvailable("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "eventDate"));

    }

    @Test
    void test1_3findEvents_whenSortViewAvalibleFalse() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        Page<Event> eventPage = new PageImpl<>(List.of(new Event()));
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true,
                "2022-09-09 00:00:00", "2022-09-10 00:00:00", false, "VIEWS", 0, 10,
                "ip", "/events");
        when(repository.findAllForPublic("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "views")))
                .thenReturn(eventPage);
        service.findEvents(param);
        verify(repository, times(1)).findAllForPublic("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "views"));

    }

    @Test
    void test1_3findEvents_whenSortEventDateAvalibleFalse() throws IncorrectPageValueException, IllegalTimeException {
        Long[] catIds = {1L};
        Page<Event> eventPage = new PageImpl<>(List.of(new Event()));
        EndpointHitDto endpointHitDto = new EndpointHitDto(null, "ewm-main-service", "/events", "ip",
                formatter.format(LocalDateTime.now()));
        ParametersPublicEventFind param = new ParametersPublicEventFind("text", catIds, true,
                "2022-09-09 00:00:00", "2022-09-10 00:00:00", false, "EVENT_DATE", 0, 10,
                "ip", "/events");
        when(repository.findAllForPublic("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "eventDate")))
                .thenReturn(eventPage);
        service.findEvents(param);
        verify(repository, times(1)).findAllForPublic("text", List.of(1L),
                true, LocalDateTime.parse("2022-09-09 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.parse("2022-09-10 00:00:00", formatter).toEpochSecond(ZoneOffset.UTC),
                PageParam.createPageable(0, 10, "eventDate"));
    }

    @Test
    void test2_1findById() throws NotFoundException {
        when(repository.findById(1L))
                .thenReturn(Optional.of(new Event()));
        service.findById(1L, "/event", "ip");
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void test2_2findById() throws NotFoundException {
        when(repository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.findById(1L, "/event", "ip");
        });
    }

    @Test
    void test2_1findById_whenClientException() throws NotFoundException {
        when(repository.findById(1L))
                .thenReturn(Optional.of(new Event()));
        doThrow(RuntimeException.class).when(client).post(any(EndpointHitDto.class));
        service.findById(1L, "/event", "ip");

    }
}