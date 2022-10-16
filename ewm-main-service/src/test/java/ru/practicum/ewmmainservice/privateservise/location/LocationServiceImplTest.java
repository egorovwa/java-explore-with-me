package ru.practicum.ewmmainservice.privateservise.location;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.ewmmainservice.models.location.Location;
import ru.practicum.ewmmainservice.models.location.dto.LocationDtoMaper;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    final LocationRepository repository = Mockito.mock(LocationRepository.class);
    final LocationDtoMaper locationDtoMaper = new LocationDtoMaper();


// TODO: 10.10.2022 new tests
}