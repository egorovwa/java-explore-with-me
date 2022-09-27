package ru.practicum.ewmmainservice.privateservise.participationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;
import ru.practicum.ewmmainservice.models.participationRequest.RequestStatus;
import ru.practicum.ewmmainservice.models.participationRequest.dto.ParticipationRequestDto;

import java.util.Collection;
import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    Collection<ParticipationRequest> findAllByRequesterId(Long requesterId); // TODO: 24.09.2022 тест обязательно
    Collection<ParticipationRequest> findAllByEventIdAndStatus(Long rventId, RequestStatus status); // TODO: 24.09.2022 test

    List<ParticipationRequestDto> findAllByRequesterIdAndEventId(Long userId, Long eventId);
}
