package ru.practicum.ewmmainservice.exceptions;

public class StatusException extends Exception{
    String reason = "For the requested operation the conditions are not met.";

    public String getReason() {
        return reason;
    }

    public StatusException(String message) {
        super(message);

    }
}
