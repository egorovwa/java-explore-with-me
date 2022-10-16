package ru.practicum.ewmmainservice.models.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class LocationForEventDto {
        private Long id;
        private String name;
        private double lat;
        private double lon;
        private int radius;
        private Long parentId;
    }

