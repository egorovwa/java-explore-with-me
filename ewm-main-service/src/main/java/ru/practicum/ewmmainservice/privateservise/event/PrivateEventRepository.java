package ru.practicum.ewmmainservice.privateservise.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.event.Event;

import java.util.List;

@Repository
public interface PrivateEventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorId(Long userId);

    @Query("SELECT e FROM Event e WHERE (LOWER(e.description) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
            "LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%') ) OR LOWER(e.title) LIKE LOWER(CONCAT('%', :text, '%')))" +
            "AND e.category.id IN :catIds AND e.paid = :paid AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd" +
            " AND e.participants.size < e.participantLimit")
    Page<Event> findAllForPublicAvailable(String text, List<Long> catIds, Boolean paid, Long rangeStart, Long rangeEnd,
                                          Pageable pageable);
    @Query("SELECT e FROM Event e WHERE (LOWER(e.description) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
            "LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%') ) OR LOWER(e.title) LIKE LOWER(CONCAT('%', :text, '%')))" +
            "AND e.category.id IN :catIds AND e.paid = :paid AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd")
    Page<Event> findAllForPublic(String text, List<Long> catIds, Boolean paid, Long rangeStart, Long rangeEnd,
                                 Pageable pageable);
}
