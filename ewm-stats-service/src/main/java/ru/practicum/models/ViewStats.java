package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewStats {
    String app;    //Название сервиса

    String uri;    //URI сервиса

    Long hits;        //Количество просмотров
}
