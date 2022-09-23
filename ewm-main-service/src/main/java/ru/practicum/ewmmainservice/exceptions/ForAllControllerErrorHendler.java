package ru.practicum.ewmmainservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@RestControllerAdvice("ru.practicum.ewmmainservice")
public class ForAllControllerErrorHendler {
    @ExceptionHandler(IncorrectPageValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError incorrectPageValueException(IncorrectPageValueException e){
        return ApiError.builder()
                .reason(String.format("The page parameter %s with the value %s is not correct.",e.getParam(), e.value))
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .description("The from parameter cannot be negative, the size parameter must be greater than 0.")
                .timestamp(Timestamp.from(Instant.now()))
                .build();
    }
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
    @ExceptionHandler(FiledParamNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError filedParamNotFoundException(FiledParamNotFoundException e){
        return ApiError.builder()
                .message(e.getMessage())
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .reason("One or more class fields do not exist.")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
