package ru.practicum.ewmmainservice.privateservise.participationrequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.participationrequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationrequest.RequestStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Collection<ParticipationRequest> findAllByRequesterId(Long requesterId);

    Collection<ParticipationRequest> findAllByEventIdAndStatus(Long rventId, RequestStatus status);

    List<ParticipationRequest> findAllByEventId(Long eventId);
}
