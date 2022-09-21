package ru.practicum.ewmmainservice.privateservise.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.models.location.Location;

import java.util.Optional;

public interface LacationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(Float lat, Float lon);
}
