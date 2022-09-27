package ru.practicum.ewmmainservice.privateservise.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.List;

@Repository
public interface PrivateEventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorId(Long userId);
}
