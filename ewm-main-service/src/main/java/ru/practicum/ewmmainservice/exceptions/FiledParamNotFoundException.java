package ru.practicum.ewmmainservice.exceptions;

public class FiledParamNotFoundException extends UserNotFoundException{
    public FiledParamNotFoundException(String message, String param, String value) {
        super(message, param, value);
    }
}
