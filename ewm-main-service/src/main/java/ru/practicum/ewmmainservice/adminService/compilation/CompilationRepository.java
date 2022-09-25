package ru.practicum.ewmmainservice.adminService.compilation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.compilation.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
