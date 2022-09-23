package ru.practicum.ewmmainservice.exceptions;

public class NotFoundException extends Exception{
    String param;
    String value;
    String className;

    public NotFoundException(String message, String param, String value, String className) {
        super(message);
        this.param = param;
        this.value = value;
        this.className = className;
    }

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }

    public String getClassName() {
        return className;
    }
}
