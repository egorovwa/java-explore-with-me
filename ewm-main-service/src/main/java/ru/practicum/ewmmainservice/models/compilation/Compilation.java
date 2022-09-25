package ru.practicum.ewmmainservice.models.compilation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ru.practicum.ewmmainservice.models.event.Event;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Compilation {
    @OneToMany
    Collection<Event> events;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull
    Boolean pinned;
    @NonNull
    String title;
}
