package ru.practicum.ewmmainservice.exceptions;

public class ModelAlreadyExistsException extends Exception {
    final String param;
    final String value;
    final String className;
    final String reason;

    public ModelAlreadyExistsException(String param, String value, String className) {
        super(String.format("%s %s = %s already exist.", className, param, value));
        this.param = param;
        this.value = value;
        this.className = className;
        reason = "Object already exist.";
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


