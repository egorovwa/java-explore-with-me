package ru.practicum.ewmmainservice.adminService.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.Collection;

@Repository
public interface AdminEwentRepository extends JpaRepository<Event, Long> {
    Collection<Event> findAllByCategoryId(Long catId);
}
