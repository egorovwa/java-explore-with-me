package ru.practicum.ewmmainservice.adminService.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
