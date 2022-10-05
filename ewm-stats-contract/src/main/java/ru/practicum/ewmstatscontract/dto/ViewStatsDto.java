package ru.practicum.ewmstatscontract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsDto {
    String app;    //Название сервиса

    String uri;    //URI сервиса

    Long hits;        //Количество просмотров
}

