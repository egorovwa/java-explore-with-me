package ru.practicum.ewmmainservice.adminService.user;

import ru.practicum.ewmmainservice.models.user.dto.NewUserDto;
import ru.practicum.ewmmainservice.models.user.dto.UserDto;

public interface UserAdminService {
    UserDto addNewUser(NewUserDto newUserDto);
}
