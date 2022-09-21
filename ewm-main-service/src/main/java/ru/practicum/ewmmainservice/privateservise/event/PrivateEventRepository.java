package ru.practicum.ewmmainservice.privateservise.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.event.Event;
@Repository
public interface PrivateEventRepository extends JpaRepository<Event, Long> {
}
