package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.user.User;
@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {

}
