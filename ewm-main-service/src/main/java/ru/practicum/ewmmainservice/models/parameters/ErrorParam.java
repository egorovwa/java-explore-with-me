package ru.practicum.ewmmainservice.models.parameters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorParam {
    private String className;
    private String param;
    private String value;
}
