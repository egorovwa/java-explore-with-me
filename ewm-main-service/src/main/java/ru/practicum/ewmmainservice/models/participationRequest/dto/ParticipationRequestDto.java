package ru.practicum.ewmmainservice.models.participationRequest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.participationRequest.RequestStatus;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    String created;
    Long event;
    Long id;
    Long requester;
    RequestStatus status;
}
