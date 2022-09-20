package ru.practicum.ewmmainservice.adminService.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.UserAlreadyExistsException;
import ru.practicum.ewmmainservice.exceptions.UserNotFoundException;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice("ru.practicum.ewmmainservice.adminService.user")
public class UserAdminErrorHendler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationError(MethodArgumentNotValidException e) {
        return ApiError.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Timestamp.from(Instant.now()))
                .reason("One or more fields are not valid.")
                .errors(e.getStackTrace())
                .build();
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError alreadyExistsException(UserAlreadyExistsException e){
        return ApiError.builder()
                .description(String.format("The user with email %s already exists.", e.getValue()))
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("User Already Exists")
                .build();
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(UserNotFoundException e){
        return ApiError.builder()
                .description(String.format("The user with id %s  not found.", e.getValue()))
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .timestamp(Timestamp.from(Instant.now()))
                .reason("User Not Found")
                .build();
    }
}
