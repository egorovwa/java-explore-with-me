package ru.practicum.ewmmainservice.exceptions;

public class UserNotFoundException extends Exception{
    String param;
    String value;

    public UserNotFoundException(String message, String param, String value) {
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
