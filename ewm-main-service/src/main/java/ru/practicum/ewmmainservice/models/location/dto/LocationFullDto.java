package ru.practicum.ewmmainservice.models.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.location.Coordinates;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationFullDto implements Coordinates {
    private Long id;
    private String name;
    @NotNull
    private double lat;
    @NotNull
    private double lon;
    private int radius;
    private Long parentId;
    private Collection<LocationFullDto> childs; // TODO: 06.10.2022 или Целиком

}
