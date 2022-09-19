package ru.practicum.ewmmainservice.models.event;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    String annotation;
    /**
     * string
     * maxLength: 2000
     * minLength: 20
     * example: Сплав на байдарках похож на полет.
     * Краткое описание события
     */
    Long category;
    /**
     * integer($int64)
     * example: 2
     * id категории к которой относится событие
     */
    String description;
    /**
     * string
     * maxLength: 7000
     * minLength: 20
     * example: Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.
     * Полное описание события
     */
    Long eventDate; // TODO: 19.09.2022 maper
    /**
     * string
     * example: 2024-12-31 15:10:05
     * Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
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
    Boolean requestModeration;
    /*	boolean
example: false
default: true
Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события. Если false - то будут подтверждаться автоматически.
*/
    String title;
    /**    string
     maxLength: 120
     minLength: 3
     example: Сплав на байдарках
     Заголовок события*/
}
