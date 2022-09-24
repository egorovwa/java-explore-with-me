package ru.practicum.ewmmainservice.privateservise.participationRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmmainservice.exceptions.*;
import ru.practicum.ewmmainservice.models.apiError.ApiError;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ParticipationRequestErrorHendler {
    @ExceptionHandler(NumberParticipantsExceededException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError numberParticipantsExceededException(NumberParticipantsExceededException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Maximum number of participants.")
                .status(HttpStatus.FORBIDDEN)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFoundException(NotFoundException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason(String.format("%s not found.", e.getClassName()))
                .status(HttpStatus.NOT_FOUND)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(FiledParamNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError filedParamNotFoundException(FiledParamNotFoundException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason("One or more filed not found")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(EventStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError eventStatusException(EventStatusException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Illegal Event state ")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
    @ExceptionHandler(IlegalUserIdException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError illegalUserIdException(IlegalUserIdException e){
        return ApiError.builder()
                .message(e.getMessage())
                .reason("Illegal user id.")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
    }
}
