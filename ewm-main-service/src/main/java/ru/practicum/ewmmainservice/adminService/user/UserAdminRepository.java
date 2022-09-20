package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmmainservice.models.user.User;

public interface UserAdminRepository extends JpaRepository<User, Long> {
}
