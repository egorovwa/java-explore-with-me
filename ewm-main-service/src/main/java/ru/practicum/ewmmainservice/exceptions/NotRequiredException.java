package ru.practicum.ewmmainservice.exceptions;

public class NotRequiredException extends Exception {
    String reason = "No action is required.";

    public NotRequiredException(String message) {
        super(message);
    }

    public String getReason() {
        return reason;
    }
}
