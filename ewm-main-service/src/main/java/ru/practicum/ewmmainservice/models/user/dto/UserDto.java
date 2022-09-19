package ru.practicum.ewmmainservice.models.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Data
public class UserDto {
    @Email
    private String email;
    @Size(min = 3)
    private String name;
}
