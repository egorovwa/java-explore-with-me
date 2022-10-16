package ru.practicum.ewmmainservice.exceptions;

public class LocationException extends Exception{
    String reason;

    public LocationException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
