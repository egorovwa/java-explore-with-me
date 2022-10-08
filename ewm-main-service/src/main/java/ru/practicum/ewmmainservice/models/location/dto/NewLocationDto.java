package ru.practicum.ewmmainservice.models.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmmainservice.models.location.Coordinates;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static ru.practicum.ewmmainservice.utils.Constants.LOCATION_MIN_SIZE;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewLocationDto implements Coordinates {
    @NotNull
    private String name;
    @NotNull
    private double lat;
    @NotNull
    private double lon;
    @Min(LOCATION_MIN_SIZE)
    private Integer radius;
    Long parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
