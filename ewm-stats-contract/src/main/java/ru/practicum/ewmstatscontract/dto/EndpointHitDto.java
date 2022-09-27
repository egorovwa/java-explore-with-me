package ru.practicum.ewmstatscontract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHitDto {
    Long id;        //   Идентификатор записи

    String app;     //    Идентификатор сервиса для которого записывается информация

    String uri;     //URI для которого был осуществлен запрос

    String ip;      //адрес пользователя, осуществившего запрос

    String timestamp;   //Дата и время, когда был совершен запрос к эндпоинту(в формате "yyyy-MM-dd HH:mm:ss")
}
