package ru.practicum.ewmmainservice.models.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.participationRequest.ParticipationRequest;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Data
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
    private Category category; // TODO: 20.09.2022 или объект
    @NotNull
    private Long createdOn;
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    private Long eventDate; // TODO: 19.09.2022 maper
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
    @OneToMany
    private Collection<User> participants = new ArrayList<>(); // подтвержденные участники

    public Event(String annotation, Category category, long createdOn, String description,
                 long eventDate, User initiator, Location location, Boolean paid, int participantLimit,
                 Long publishedOn, Boolean requestModeration, EventState state, String title) {
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

    }
}
