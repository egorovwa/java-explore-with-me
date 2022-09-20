package ru.practicum.ewmmainservice.models.category;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "categorys")
public class Category { // TODO: 20.09.2022 dto может и не надо
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    @Size(min = 3, max = 50)
    String name;
}
