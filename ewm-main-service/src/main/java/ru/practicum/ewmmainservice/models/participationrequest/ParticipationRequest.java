package ru.practicum.ewmmainservice.models.participationrequest;

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
@Table(name = "participationrequests", uniqueConstraints = {@UniqueConstraint(columnNames = {"event_id", "requester_id"})})
public class ParticipationRequest {
    @NotNull
    private Long created;
    @ManyToOne
    @NotNull
    private Event event;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @NotNull
    private User requester;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
