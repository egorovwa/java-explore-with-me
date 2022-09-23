package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.ModelAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.NotFoundException;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.Instant;

@RestControllerAdvice("ru.practicum.ewmmainservice.adminService.user")
public class UserAdminErrorHendler {
    @ExceptionHandler(ModelAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError alreadyExistsException(ModelAlreadyExistsException e){
        return ApiError.builder()
                .description(String.format("The user with email %s already exists.", e.getValue()))
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("User Already Exists")
                .build();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(NotFoundException e){
        return ApiError.builder()
                .description(String.format("The user with id %s  not found.", e.getValue()))
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("User Not Found")
                .build();
    }
}
