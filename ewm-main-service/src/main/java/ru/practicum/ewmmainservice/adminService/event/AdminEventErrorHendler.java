package ru.practicum.ewmmainservice.adminService.event;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.EventStatusException;
import ru.practicum.ewmmainservice.exceptions.IllegalTimeException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.io.NotActiveException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice("ru.practicum.ewmmainservice.adminService.event")
public class AdminEventErrorHendler {
    @ExceptionHandler(EventStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError eventStatusException(EventStatusException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Event state mast be Waiting")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    @ExceptionHandler(IllegalTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError illegalTimeExceptin(IllegalTimeException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .reason("The time of the event must be at least an hour later.")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError notFoundException(NotFoundException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Event not found.")
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
