package ru.practicum.ewmmainservice.models.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    String name;
}
