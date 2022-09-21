package ru.practicum.ewmmainservice.privateservise.participationRequest;

import lombok.Data;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "participationrequests")
public class ParticipationRequest {
    Long created;
    @ManyToOne
    @NotNull
    Event event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @NotNull
    User requester;
    RequestStatus status;
}
