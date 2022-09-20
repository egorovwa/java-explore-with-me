package ru.practicum.ewmmainservice.models.event;

import lombok.Data;
import ru.practicum.ewmmainservice.models.category.Category;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    User creator;
    @NotNull
    @Size(max = 2000, min = 20)
    String annotation;
    /**
     * string
     * maxLength: 2000
     * minLength: 20
     * example: Сплав на байдарках похож на полет.
     * Краткое описание события
     */
    @NotNull
    @ManyToOne
    Category category; // TODO: 20.09.2022 или объект
    /**
     * integer($int64)
     * example: 2
     * id категории к которой относится событие
     */
    @Size(min = 20, max = 7000)
    String description;
    /**
     * string
     * maxLength: 7000
     * minLength: 20
     * example: Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.
     * Полное описание события
     */
    @NotNull
    Long eventDate; // TODO: 19.09.2022 maper
    /**
     * string
     * example: 2024-12-31 15:10:05
     * Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    @ManyToOne
    Location location;
    /**
     * Location{...}
     * <p>
     * paid	boolean
     * example: true
     * default: false
     * Нужно ли оплачивать участие в событии
     */
    int participantLimit;
    /*	integer($int32)
    example: 10
    default: 0
    Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
*/
    @NotNull
    Boolean requestModeration;
    /*	boolean
example: false
default: true
Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
*/
    @NotBlank
    @Size(min = 3, max = 120)
    String title;
    /**
     * string
     * maxLength: 120
     * minLength: 3
     * example: Сплав на байдарках
     * Заголовок события
     */
    @OneToMany
    Collection<User> participants; // подтвержденные участники
    @OneToMany
    Collection<User> participationRequests; // запрорсы на участие
    long quantityRequests; // TODO: 20.09.2022 расчет в бд если получиться
}
