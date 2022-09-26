package ru.practicum.ewmmainservice.adminService.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.event.EventState;

import java.util.Collection;
import java.util.List;

@Repository
public interface AdminEwentRepository extends JpaRepository<Event, Long> {
    Collection<Event> findAllByCategoryId(Long catId);
 @Query("SELECT e FROM Event e WHERE e.initiator.id IN :initiatorId AND e.category.id IN :categoryId " +
         "AND e.state IN :states AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd")
    List<Event> findForAdmin(List<Long> initiatorId, List<Long> categoryId, List<EventState> states,
                        Long rangeStart, Long rangeEnd);
}
