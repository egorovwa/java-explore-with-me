package ru.practicum.ewmmainservice.privateservise.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDto;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    final LocationRepository repository = Mockito.mock(LocationRepository.class);
    final LocationDtoMaper locationDtoMaper = new LocationDtoMaper();
    final LocationServiceImpl service = new LocationServiceImpl(repository, locationDtoMaper);

    @Test
    void findByLatAndLon() {
        service.findByLatAndLon(1f, 2f);
        Mockito.verify(repository, Mockito.times(1)).findByLatAndLon(1f, 2f);
    }

    @Test
    void save() {
        Location location = new Location(null, 1f, 2f);
        LocationDto locationDto = new LocationDto(1f, 2f);
        service.save(locationDto);
        Mockito.verify(repository, Mockito.times(1)).save(location);
    }
}