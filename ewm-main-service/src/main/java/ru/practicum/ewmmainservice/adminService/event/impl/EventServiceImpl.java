package ru.practicum.ewmmainservice.adminService.event.impl;

import org.springframework.stereotype.Service;
import ru.practicum.ewmmainservice.adminService.event.EventService;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.Collection;
@Service
public class EventServiceImpl implements EventService {
    @Override
    public Collection<Event> findByCategoryId(Long catId) {
        return null;
    }
}
