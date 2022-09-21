package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.user.User;

import java.util.Arrays;
import java.util.List;
@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {

}
