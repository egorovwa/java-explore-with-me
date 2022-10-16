package ru.practicum.ewmmainservice.models.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.location.Coordinates;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationShortDto implements Coordinates {
    private Long id;
    private String name;
    private double lat;
    private double lon;
    private int radius;
    private Long parentId;
    private Boolean approved;
    private Collection<Long> childs;
}
