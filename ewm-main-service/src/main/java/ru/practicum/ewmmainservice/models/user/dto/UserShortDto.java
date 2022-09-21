package ru.practicum.ewmmainservice.models.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto {
    Long id;
    @Email
    private String name;
}
