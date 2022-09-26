package ru.practicum.ewmmainservice.exceptions;

public class RuntimeNotFoundException extends RuntimeException{
    String param;
    String value;
    String className;

    public String getParam() {
        return param;
    }

    public String getValue() {
        return value;
    }

    public String getClassName() {
        return className;
    }

    public RuntimeNotFoundException(String param, String value, String className) {
        this.param = param;
        this.value = value;
        this.className = className;
    }
}
