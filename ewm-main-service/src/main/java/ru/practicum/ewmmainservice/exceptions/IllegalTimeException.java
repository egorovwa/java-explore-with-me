package ru.practicum.ewmmainservice.exceptions;

public class IllegalTimeException extends Exception {
    String time;
    String reason;

    public IllegalTimeException(String message, String time) {
        super(message);
        this.time = time;
        reason = "Time error.";
    }

    public String getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }
}
