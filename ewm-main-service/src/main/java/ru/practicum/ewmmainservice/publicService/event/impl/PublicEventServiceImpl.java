package ru.practicum.ewmmainservice.publicService.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventShortDto;
import ru.practicum.ewmmainservice.models.parameters.ParametersPublicEventFind;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.publicService.client.EwmClient;
import ru.practicum.ewmmainservice.publicService.event.PublicEventService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventServiceImpl implements PublicEventService {
    private final EwmClient client;
    private final PrivateEventRepository repository;
    private final EventDtoMaper dtoMaper;

    @Override
    public Collection<EventShortDto> findEfents(ParametersPublicEventFind param) {
        if (param.getOnlyAvailable()) {
            return repository.findAllForPublicAvailable(param.getText(), param.getCatIds(), param.getPaid(),
                    param.getRangeStart(), param.getRangeEnd(), param.getPageable()).map(dtoMaper::toShortDto)
                    .toList();
        }else {
            Page<Event> eventPage = repository.findAllForPublic(param.getText(), param.getCatIds(), param.getPaid(),
                    param.getRangeStart(), param.getRangeEnd(), param.getPageable());
        }
        return null;
    }
}
