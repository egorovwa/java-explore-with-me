package ru.practicum.ewmmainservice.exceptions;

public class StatusException extends Exception {
    final String reason = "For the requested operation the conditions are not met.";

    public StatusException(String message) {
        super(message);

    }

    public String getReason() {
        return reason;
    }
}
