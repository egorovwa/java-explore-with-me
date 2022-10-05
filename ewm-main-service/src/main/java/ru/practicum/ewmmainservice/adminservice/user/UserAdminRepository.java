package ru.practicum.ewmmainservice.adminservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmmainservice.models.user.User;

import java.util.List;

@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.id IN (:ids)")
    Page<User> findAllById(List<Long> ids, Pageable pageable);
}
