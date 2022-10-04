package ru.practicum.ewmmainservice.models.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 2000, min = 20)
    private String annotation;
    @NotNull
    @ManyToOne
    private Category category;
    @NotNull
    private Long createdOn;
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private Long eventDate;
    @ManyToOne
    @NotNull
    private User initiator;
    @ManyToOne
    @NotNull
    private Location location;
    @NotNull
    private Boolean paid;
    int participantLimit;
    private Long publishedOn; // опубликованно дата
    @NotNull
    private Boolean requestModeration;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EventState state;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
    private int views; // кол-во поросмоьров
    @OneToMany(fetch = FetchType.EAGER)
    private Collection<User> participants; // подтвержденные участники

    public Event(String annotation, Category category, long createdOn, String description,
                 long eventDate, User initiator, Location location, Boolean paid, int participantLimit,
                 Long publishedOn, Boolean requestModeration, EventState state, String title, Collection<User> participants) {
        this.annotation = annotation;
        this.category = category;
        this.createdOn = createdOn;
        this.description = description;
        this.eventDate = eventDate;
        this.initiator = initiator;
        this.location = location;
        this.paid = paid;
        this.participantLimit = participantLimit;
        this.publishedOn = publishedOn;
        this.requestModeration = requestModeration;
        this.state = state;
        this.title = title;
        this.participants = participants;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        Boolean isParticipantsEquals = participants.containsAll(event.participants);
        return participantLimit == event.participantLimit
                && views == event.views
                && Objects.equals(id, event.id)
                && Objects.equals(annotation, event.annotation)
                && Objects.equals(category, event.category)
                && Objects.equals(createdOn, event.createdOn)
                && Objects.equals(description, event.description)
                && Objects.equals(eventDate, event.eventDate)
                && Objects.equals(initiator, event.initiator)
                && Objects.equals(location, event.location)
                && Objects.equals(paid, event.paid)
                && Objects.equals(publishedOn, event.publishedOn)
                && Objects.equals(requestModeration, event.requestModeration)
                && state == event.state && Objects.equals(title, event.title)
                && isParticipantsEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, annotation, category, createdOn, description, eventDate, initiator, location, paid, participantLimit, publishedOn, requestModeration, state, title, views, participants);
    }
}
