package ru.practicum.ewmmainservice.models.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NewUserDto {
    @Email
    @NotNull
    private String email;
    @NotBlank
    private String name;
}
