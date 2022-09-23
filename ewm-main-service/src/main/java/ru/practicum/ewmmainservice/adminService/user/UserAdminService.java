package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.user.User;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;

import java.util.Collection;

public interface UserAdminService {
    UserDto addNewUser(NewUserDto newUserDto) throws ModelAlreadyExistsException;

    void deleteUser(Long userId) throws NotFoundException;

    Collection<UserDto> findAll(Pageable pageable);


    Collection<UserDto> findByIds(Long[] ids);

    User findById(Long userId) throws NotFoundException;
}
