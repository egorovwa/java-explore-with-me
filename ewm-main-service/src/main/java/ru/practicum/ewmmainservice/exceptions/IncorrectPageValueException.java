package ru.practicum.ewmmainservice.exceptions;

public class IncorrectPageValueException extends Exception {
    final String param;
    final String value;

    public IncorrectPageValueException(String message, String param, String value) {
        super(message);
        this.param = param;
        this.value = value;
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }
}
