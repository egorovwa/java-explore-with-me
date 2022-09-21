package ru.practicum.ewmmainservice.models.user.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewmmainservice.models.user.User;

@Component
public class UserDtoMaper {
    public User fromCreateDto(NewUserDto dto) {
        return new User(dto.getEmail(), dto.getName());
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    public UserShortDto toShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }
}
