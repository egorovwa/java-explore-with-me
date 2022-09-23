package ru.practicum.ewmmainservice.privateservise.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice("ru.practicum.ewmmainservice.privateservise.event")
public class PrivateEventErrorHendler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
public ApiError notFoundException(NotFoundException e){
        return ApiError.builder()
                .reason("The required object was not found.")
                .message(e.getMessage())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(EventStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError eventStatusException(EventStatusException e){
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(IllegalTimeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError illegalTimeException(IllegalTimeException e){
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.FORBIDDEN)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
