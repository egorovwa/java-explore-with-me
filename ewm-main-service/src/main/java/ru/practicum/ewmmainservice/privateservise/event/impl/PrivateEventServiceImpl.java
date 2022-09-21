package ru.practicum.ewmmainservice.privateservise.event.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.category.CategoryService;
import ru.practicum.ewmmainservice.adminService.user.UserAdminService;
import ru.practicum.ewmmainservice.exceptions.FiledParamNotFoundException;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.event.dto.EventDtoMaper;
import ru.practicum.ewmmainservice.models.event.dto.EventFullDto;
import ru.practicum.ewmmainservice.models.event.dto.NewEventDto;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventRepository;
import ru.practicum.ewmmainservice.privateservise.event.PrivateEventService;
import ru.practicum.ewmmainservice.privateservise.location.LocationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrivateEventServiceImpl implements PrivateEventService {
    private final CategoryService categoryService;
    private final UserAdminService userAdminService;
    private final PrivateEventRepository repository;
    private final EventDtoMaper eventDtoMaper;
    private final LocationService locationService;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto newEventDto) throws FiledParamNotFoundException {

        try { // TODO: 21.09.2022 dublicate?
            Category category = categoryService.findByid(newEventDto.getCategory());
            User user = userAdminService.findById(userId);
            Location location = locationService
                    .findByLatAndLon(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon())
                    .orElseGet(()-> locationService.save(newEventDto.getLocation()));
            return eventDtoMaper.toFulDto(repository.save(eventDtoMaper.fromNewDto(newEventDto, category, user, location)));
        } catch (UserNotFoundException e) {
            throw new FiledParamNotFoundException("Category not found", "id", newEventDto.getCategory().toString());
        }
    }
}
