package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;

import java.util.Collection;

public interface UserAdminService {
    UserDto addNewUser(NewUserDto newUserDto) throws UserAlreadyExistsException;

    void deleteUser(Long userId) throws UserNotFoundException;

    Collection<UserDto> findAll(Pageable pageable);


    Collection<UserDto> findByIds(Long[] ids);
}
