package ru.practicum.ewmmainservice.models.location.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.ewmmainservice.models.location.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationDtoMaperTest {
LocationDtoMaper maper = new LocationDtoMaper();
    @Test
    void toDto() {
        Location location = new Location(1L, 1.0f, 2.0f);
        LocationDto dto = new LocationDto(1.0f,2.0f );
        assertEquals(maper.toDto(location), dto);
    }

    @Test
    void fromDto() {
        Location location = new Location(null, 1.0f, 2.0f);
        LocationDto dto = new LocationDto(1.0f,2.0f );
        assertEquals(maper.fromDto(dto), location);
    }
}