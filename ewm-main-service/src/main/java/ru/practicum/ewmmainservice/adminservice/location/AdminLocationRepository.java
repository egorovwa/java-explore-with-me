package ru.practicum.ewmmainservice.adminservice.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.location.Location;

import java.util.Collection;

@Repository
public interface AdminLocationRepository extends JpaRepository<Location, Long> {
    @Query(value = "SELECT * FROM locations loc WHERE (distance(loc.lat, loc.lon, :lat, :lon)) < :radius", nativeQuery = true)
    Collection<Location> findChilds(double lat, double lon, double radius);

    @Query(value = "SELECT  * FROM locations loc" +
            " WHERE (distance(:lat, :lon, loc.lat, loc.lon) < loc.radius /1000)  AND loc.radius > :radius ORDER BY loc.radius"
            , nativeQuery = true)
    Collection<Location> findParents(double lat, double lon, int radius);

    Page<Location> findAllByApproved(Boolean approved, Pageable pageable);
}
