package ru.practicum.ewmmainservice.exceptions;

import java.time.LocalDateTime;

public class IllegalTimeException extends Exception {
    String time;

    public IllegalTimeException(String message, String time) {
        super(message);
        this.time = time;
    }
}
