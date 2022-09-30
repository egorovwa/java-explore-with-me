package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //   Идентификатор записи
    @NonNull
    private String app;     //    Идентификатор сервиса для которого записывается информация
    @NonNull
    private String uri;     //URI для которого был осуществлен запрос
    @NonNull
    private String ip;      //адрес пользователя, осуществившего запрос
    @NonNull
    private Long timestamp;   //Дата и время, когда был совершен запрос к эндпоинту(в формате "yyyy-MM-dd HH:mm:ss")
}
