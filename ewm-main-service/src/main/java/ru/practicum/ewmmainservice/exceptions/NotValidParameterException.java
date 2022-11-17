package ru.practicum.ewmmainservice.exceptions;

import ru.practicum.ewmmainservice.models.parameters.ErrorParam;

import java.util.List;

public class NotValidParameterException extends Exception {
    List<ErrorParam> exceptionList;
    String reason;

    public List<ErrorParam> getExceptionList() {
        return exceptionList;
    }

    public String getReason() {
        return reason;
    }

    public NotValidParameterException(String message, List<ErrorParam> errors) {
        super(message);
        this.exceptionList = errors;
    }
}
