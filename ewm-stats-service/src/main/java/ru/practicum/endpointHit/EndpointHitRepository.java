package ru.practicum.endpointHit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.models.EndpointHit;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
}
