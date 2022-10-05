package ru.practicum.ewmmainservice.models.participationrequest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.participationrequest.RequestStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private RequestStatus status;
}
