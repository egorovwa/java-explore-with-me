package ru.practicum.ewmmainservice.exceptions;

public class ModelAlreadyExistsException extends Exception{
    String param;
    String value;

    public ModelAlreadyExistsException(String message, String param, String value) {
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
