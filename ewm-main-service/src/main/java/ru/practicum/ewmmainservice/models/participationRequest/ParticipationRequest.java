package ru.practicum.ewmmainservice.models.participationRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.event.Event;
import ru.practicum.ewmmainservice.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "participationrequests",uniqueConstraints = {@UniqueConstraint(columnNames = {"event_id", "requester_id"})})
public class ParticipationRequest {
    @NotNull
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
    @NotNull
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
