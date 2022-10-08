package ru.practicum.ewmmainservice.privateservise.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.location.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(double lat, double lon);

    @Query(value = "SELECT distance(:lat1, :lan1, :lat2, :lan2)", nativeQuery = true)
    float distant(double lat1, double lan1, double lat2, double lan2);
}
