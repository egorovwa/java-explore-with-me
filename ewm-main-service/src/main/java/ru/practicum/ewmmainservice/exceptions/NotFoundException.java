package ru.practicum.ewmmainservice.exceptions;

public class NotFoundException extends Exception{
    String param;
    String value;
    String className;
    String reason;

    public NotFoundException(String param, String value, String className) {
        super(String.format("%s %s %s not found.", className, param, value));
        this.param = param;
        this.value = value;
        this.className = className;
        reason = "The required object was not found.";
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

    public String getReason() {
        return reason;
    }
}
